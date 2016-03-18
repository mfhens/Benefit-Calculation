package org.kombit.core.model

import org.joda.time.DateTime

class SubsistencePeriod(startDate: DateTime, endDate: DateTime, val subsistencePeriodType: SubsistencePeriodType = Subsistence, val workingHours: Int = 0) extends RegistrationPeriod(startDate, endDate) 
{ 
  override def copy(startDate: DateTime = startDate, endDate: DateTime = endDate) = new SubsistencePeriod(startDate, endDate, subsistencePeriodType, workingHours)

  override def canEqual(other: Any) = {
    other.isInstanceOf[org.kombit.core.model.SubsistencePeriod]
  }

  override def equals(other: Any) = {
    other match {
      case that: org.kombit.core.model.SubsistencePeriod => SubsistencePeriod.super.equals(that) && that.canEqual(SubsistencePeriod.this) && subsistencePeriodType == that.subsistencePeriodType && workingHours == that.workingHours
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime * (prime * SubsistencePeriod.super.hashCode() + subsistencePeriodType.hashCode) + workingHours.hashCode
  } 
}

sealed trait SubsistencePeriodType
case object Subsistence extends SubsistencePeriodType
case object DeadPeriod extends SubsistencePeriodType
case object Working extends SubsistencePeriodType
