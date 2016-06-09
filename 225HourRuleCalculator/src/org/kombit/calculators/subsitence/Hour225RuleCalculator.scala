package org.kombit.calculators.subsitence

import org.kombit.calculators.Util._

import org.kombit.core.model._

import org.joda.time.DateTime

class Hour225RuleCalculator(val subsistencePeriods: List[SubsistencePeriod], val totalCalculationPeriodInYears: Int = 3, val workingHourRequirement: Int = 225, val workingHoursRequirementWithinYears: Int = 1) {
  
  val totalCalculationPeriodInDays = getStandardDaysInYears(totalCalculationPeriodInYears)
  val workingHoursRequirementWithinDays = getStandardDaysInYears(workingHoursRequirementWithinYears)
  
  val relevantSubsistencePeriods = alignRegistrationPeriodsWithMonths(limitToRelevantPeriods(subsistencePeriods, DateTime.now minusYears totalCalculationPeriodInYears))
  
  def transitionDate: DateTime = {
    val subsistenceList = relevantSubsistencePeriods.filter (_.subsistencePeriodType == Subsistence)
    val totalDaysWithSubsistence = subsistenceList.map (_.getDays).sum
    if (totalDaysWithSubsistence <= workingHoursRequirementWithinDays)
      DateTime.now plusDays (workingHoursRequirementWithinDays - totalDaysWithSubsistence.toInt)
    else {
      subsistenceList.foldLeft((0, DateTime.now))(
          (result, s) => if (result._1 < workingHoursRequirementWithinDays)
            (result._1 + s.getDays.toInt, s.endDate)
          else
            result
      )._2
    }    
  }
  
  def startDate: DateTime =  maxDate(List(transitionDate minusYears workingHoursRequirementWithinYears, earliestNotMeetingWorkingRequirementDate, earliestNoDeadPeriodDate))
  
  def cancellationDate: DateTime = (startDate plusYears workingHoursRequirementWithinYears) plusDays deadPeriodsInDays
  
  def earliestNotMeetingWorkingRequirementDate: DateTime = {
    val workingPeriodsByMonth = alignRegistrationPeriodsWithMonths(limitToRelevantPeriods(relevantSubsistencePeriods.filter ( _.subsistencePeriodType == Working ), DateTime.now minusYears workingHoursRequirementWithinYears))
    workingPeriodsByMonth.foldRight((DateTime.now, 0))(
        (s, result) => if (result._2 < workingHourRequirement)
          (s.endDate, result._2 + s.workingHours)
        else
          result
    )._1.withDayOfMonth(1)
  }
  
  def earliestNoDeadPeriodDate: DateTime = DateTime.now plusDays Math.max(sumDeadPeriodsIn(relevantSubsistencePeriods) - getStandardDaysInYears(totalCalculationPeriodInYears - workingHoursRequirementWithinYears) , 0)
  
  def deadPeriodsInDays: Int = sumDeadPeriodsIn(limitToRelevantPeriods(relevantSubsistencePeriods, startDate)) 
  
  private def sumDeadPeriodsIn(l: List[SubsistencePeriod]) = (l withFilter (_.subsistencePeriodType == DeadPeriod) map (_.getDays) sum).toInt
}