package org.kombit.calculators

import org.kombit.core.model.RegistrationPeriod

import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.Interval
import org.joda.time.Months

package object Util {
  
  def getStandardDaysInYears(years: Int) = years*365 + years/4
  
  def getStandardDaysInMonths(months: Int) = months*31

  def mergePeriods[P <: RegistrationPeriod](periods: List[P], mergeOn: (P, P) => Boolean, mergeFunc: (P, P) => P): List[P] = 
  {
    periods.foldLeft(List[P]())((list, period) => list match {
      case Nil => List(period)
      case x :: xs => 
        if (x.abuts(period) && mergeOn(x, period))
          mergeFunc(x, period) :: xs
        else
          period :: x :: xs
    }).reverse
  }
  
  def alignRegistrationPeriodsWithYears[P <: RegistrationPeriod](periods: List[P]) = splitRegistrationPeriods[P](periods, {x => x.plusYears(1).withDayOfYear(1) })
  
  def alignRegistrationPeriodsWithMonths[P <: RegistrationPeriod](periods: List[P]) = splitRegistrationPeriods[P](periods, {x => x.plusMonths(1).withDayOfMonth(1) })
  
  private def splitRegistrationPeriods[P <: RegistrationPeriod](periods: List[P], splitDateCalc: DateTime => DateTime): List[P] = 
  {
 
    def splitRegistrationPeriodsHelper[P <: RegistrationPeriod](period : P): List[P] = {
      val splitDate = splitDateCalc(period.startDate)
      val calendarYearOfPeriod = new Interval(period.startDate, splitDate)
      
      if (calendarYearOfPeriod contains period)
        List(period)
      else
        period.copy(endDate=splitDate).asInstanceOf[P] :: splitRegistrationPeriodsHelper(period.copy(startDate=splitDate).asInstanceOf[P])
    }
    
    periods match {
      case Nil      => Nil
      case x::tail  => splitRegistrationPeriodsHelper(x) ::: splitRegistrationPeriods(tail, splitDateCalc)
    }
  }
    
  def limitToRelevantPeriods[P <: RegistrationPeriod](residencePeriods: List[P], earliestStartDate: DateTime): List[P] = 
  {
    val relevantPeriods = residencePeriods dropWhile { _ isBefore earliestStartDate}
    val head = relevantPeriods.head
    
    if (head.startDate isBefore earliestStartDate) 
      head.copy(startDate = earliestStartDate).asInstanceOf[P] :: relevantPeriods.tail
    else
      relevantPeriods
  }
  
  def earlierDate(l: DateTime, r: DateTime) = if (l isBefore r) l else r
  
  def laterDate(l: DateTime, r: DateTime) = if (l isAfter r) l else r
  
  /*
   * Implicit conversions
   */
  
  implicit def jodaMonthsToInt(x: Months) = x.getMonths
}