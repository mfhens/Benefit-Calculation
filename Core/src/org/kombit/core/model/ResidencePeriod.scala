package org.kombit.core.model

import org.joda.time.DateTime

import java.util.Date

class ResidencePeriod(startDate: DateTime, endDate: DateTime, val placeOfResidence: PlaceOfResidence, val groundsForExemption: GroundsForExemption = None) extends RegistrationPeriod(startDate, endDate) {
  
  def this(startDate: Date, endDate: Date, placeOfResidence: PlaceOfResidence, groundsForExemption: GroundsForExemption) {
    this(new DateTime(startDate), new DateTime(endDate), placeOfResidence, groundsForExemption)
  }
  
  override def copy (startDate: DateTime = startDate, endDate: DateTime = endDate) = new ResidencePeriod(startDate, endDate, placeOfResidence, groundsForExemption)

  override def canEqual(other: Any) = {
    other.isInstanceOf[org.kombit.core.model.ResidencePeriod]
  }

  override def equals(other: Any) = {
    other match {
      case that: org.kombit.core.model.ResidencePeriod => ResidencePeriod.super.equals(that) && that.canEqual(ResidencePeriod.this) && placeOfResidence == that.placeOfResidence && groundsForExemption == that.groundsForExemption
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime * (prime * ResidencePeriod.super.hashCode() + placeOfResidence.hashCode) + groundsForExemption.hashCode
  }
}

object ResidencePeriod {
  
  def mergeFunc(p1: ResidencePeriod, p2: ResidencePeriod) = p1.copy(p1.startDate, p2.endDate)
  
  def mergeOn(p1: ResidencePeriod, p2: ResidencePeriod) = 
    (p1.placeOfResidence == p2.placeOfResidence || 
        (p1.placeOfResidence != DK && p2.placeOfResidence != DK)) && 
    (p1.groundsForExemption == p2.groundsForExemption)
}

sealed trait PlaceOfResidence
case object DK extends PlaceOfResidence 
case object EU extends PlaceOfResidence
case object Other extends PlaceOfResidence 

sealed trait GroundsForExemption 
case object MilitaryService extends GroundsForExemption
case object None extends GroundsForExemption