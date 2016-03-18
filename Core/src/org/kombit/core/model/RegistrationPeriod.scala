package org.kombit.core.model

import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.Period
import org.joda.time.Duration

import java.util.Date

abstract class RegistrationPeriod(val startDate: DateTime, val endDate: DateTime) extends Equals {
  
  def this(startDate: Date, endDate: Date) = this(new DateTime(startDate), new DateTime(endDate))
  
  if (startDate isAfter endDate) throw new IllegalArgumentException("startDate must be before endDate")
  
  protected val interval = new Interval(startDate, endDate)
  
  def getDays = interval.toDuration.getStandardDays
  
  def overlaps(r : RegistrationPeriod) = interval.overlap(r)
  
  def isBefore(d : DateTime) = interval.isBefore(d)
  
  def abuts(r : RegistrationPeriod) = interval.abuts(r)
  
  def copy(startDate: DateTime = startDate, endDate: DateTime = endDate): RegistrationPeriod = ???
  
  def canEqual(other: Any) = {
    other.isInstanceOf[org.kombit.core.model.RegistrationPeriod]
  }

  override def equals(other: Any) = {
    other match {
      case that: org.kombit.core.model.RegistrationPeriod => that.canEqual(RegistrationPeriod.this) && startDate == that.startDate && endDate == that.endDate
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime * (prime + startDate.hashCode) + endDate.hashCode
  }
  
}

object RegistrationPeriod {
  implicit def registrationPeriodToInterval(x: RegistrationPeriod) : Interval = x.interval
  implicit def registrationPeriodToPeriod(x: RegistrationPeriod) : Period = x.interval.toPeriod
  implicit def registrationPeriodToDuration(x: RegistrationPeriod) : Duration = x.interval.toDuration
}