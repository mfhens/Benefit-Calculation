package org.kombit.core.model

import org.joda.time._
import org.scalatest._

class ResidencePeriodSpec extends org.kombit.core.UnitSpec {

  "Two ResidencePeriods with the same values for all attributes" should "be equal" in {
    val startDate = new DateTime(2016, 1, 1, 0, 0)
    val endDate = new DateTime(2106, 2, 2, 0, 0)
    val endDateF = new DateTime(2016, 3, 3, 0, 0)
    val startDateF = new DateTime(2016, 1, 2, 0, 0)
    
    val p1 = new ResidencePeriod(startDate, endDate, DK)
    val p2 = new ResidencePeriod(startDate, endDate, DK)
    val p3 = new ResidencePeriod(startDate, endDateF, DK)
    val p4 = new ResidencePeriod(startDate, endDate, EU)
    val p5 = new ResidencePeriod(startDate, endDate, DK, MilitaryService)
    val p6 = new ResidencePeriod(startDateF, endDate, DK)
    
    assert(p1 == p2)
    assert(p1 != p3)
    assert(p1 != p4)
    assert(p1 != p5)
    assert(p1 != p6)
  }
  
  "Start Date of a Residence Period" should "be before End Date" in {
    val startDate = new DateTime(2016, 1, 1, 0, 0)
    val endDate = new DateTime(2015, 2, 2, 0, 0)
    
    intercept[IllegalArgumentException] {
      new ResidencePeriod(startDate, endDate, DK)
    }
  }
  
  "Copying of a ResidencePeriod with no additional parameters" should "give an identical ResidencePeriod" in {
    val startDate = new DateTime(2016, 1, 1, 0, 0)
    val endDate = new DateTime(2106, 2, 2, 0, 0)
    
    val p1 = new ResidencePeriod(startDate, endDate, DK)
    
    assert(p1 == p1.copy())
  }
  
}