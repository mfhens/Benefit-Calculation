package org.kombit.calculators.integrationbenefit

import org.kombit.calculators.Util._

import org.kombit.core.model._

import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Period
import org.joda.time.Months

import java.util.Date

/**
 *  A ResidenceTimeCalculator responsibility is to calculate the earliest date an applicant
 *  is eligible for receiving Kontanthjælp instead of Integrationsydelse
 *  
 *  @constructor Initializes the ResidenceTimeCalculator with the legal timelimits used 
 *  in the calculation. The default values reflect the values as specified in the law as of 
 *  26.02.2016
 *  @param residenceRequirementInYears Minimum time applicant must have had residence in Denmark within the total relevant period
 *  @param totalCalculationPeriodInYears Total period relevant for calculation.
 *  @param maxDaysForShortForeignStay Number of Days for stays abroad within a calendar year, that are not counted
 * 
 */
class ResidenceTimeCalculator (
    val residencePeriods: List[ResidencePeriod], 
    val residenceRequirementInYears: Int = 7, 
    val totalCalculationPeriodInYears: Int = 8, 
    val maxConsecutiveMonthsForShortForeignStay: Int = 2
    )
{
  val residenceRequirementInDays = getStandardDaysInYears(residenceRequirementInYears)
  val totalCalculationPeriodInDays = getStandardDaysInYears(totalCalculationPeriodInYears)
  val maxDaysForShortForeignStay = getStandardDaysInMonths(maxConsecutiveMonthsForShortForeignStay)
  val maxDaysOfForeignResidence = totalCalculationPeriodInDays - residenceRequirementInDays
  
  /**
   * JodaTime free Interface for external usage
   */
  def entitlementDate = entitlementDateTime.toDate
  
  def entitlementDateTime: DateTime = 
  {
    val absoluteStart = residencePeriods.last.endDate minusYears totalCalculationPeriodInYears
    val alignedList = alignRegistrationPeriodsWithYears(
        mergePeriods(limitToRelevantPeriods(residencePeriods, absoluteStart), ResidencePeriod.mergeOn, ResidencePeriod.mergeFunc))
    
    val staysByYearInDays = removeShortForeignStays(periodsOfForeignStayByYear(alignedList))
    val daysOfForeignResidence = staysByYearInDays.sum + new Duration(absoluteStart, alignedList.head.startDate).getStandardDays 
    
    residencePeriods.last.endDate plusDays Math.max(daysOfForeignResidence - maxDaysOfForeignResidence, 0).toInt
  }
  
  private def periodsOfForeignStayByYear(residencePeriods: List[ResidencePeriod]) = residencePeriods filter { x => (x.placeOfResidence != DK) && (x.groundsForExemption == None) } groupBy (_.startDate.getYear())
  
  private def removeShortForeignStays(foreignStays: Map[Int, List[ResidencePeriod]]) = {
    foreignStays.values.map 
    { x => 
      val sumOfStays = x.map(_.getDays).sum
      if (mergePeriods(x, ResidencePeriod.mergeOn, ResidencePeriod.mergeFunc).exists(Months.monthsIn(_) >= maxConsecutiveMonthsForShortForeignStay)) 
        sumOfStays
      else if (sumOfStays > maxDaysOfForeignResidence) sumOfStays else 0
    }
  }
}