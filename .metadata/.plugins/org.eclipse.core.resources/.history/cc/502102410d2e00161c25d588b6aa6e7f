package org.kombit.calculators.test

import org.kombit.core.model._
import org.kombit.core.test._
import org.kombit.calculators.Util._
import org.joda.time.DateTime
import org.scalatest._

class UtilSpec extends UnitSpec {
   "The Util" should "not split periods, that are solely within a year" in {
    val p1 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2016, 8, 8, 0, 0), EU)
    
    assert(alignRegistrationPeriodsWithYears(List(p1)).head == p1)
  }
  
   it should "split periods, in months" in {
    val p1 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2016, 8, 8, 0, 0), EU)
    
    assert(alignRegistrationPeriodsWithMonths(List(p1)).length == 6)
  }
   
  it should "not merge non abut periods" in {
      val p1 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2016, 8, 8, 0, 0), EU)
      val p2 = new ResidencePeriod(new DateTime(2016, 9, 9, 0, 0), new DateTime(2016, 12, 12, 0, 0), EU)
      
      assert(mergePeriods(List(p1, p2), ResidencePeriod.mergeOn, ResidencePeriod.mergeFunc) == List(p1, p2)) 
  }
  
  it should "merge abut periods with equal data payload" in {
      val p1 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2016, 8, 8, 0, 0), EU)
      val p2 = new ResidencePeriod(new DateTime(2016, 8, 8, 0, 0), new DateTime(2016, 12, 12, 0, 0), EU)
      val p3 = new ResidencePeriod(new DateTime(2016, 12, 12, 0, 0), new DateTime(2016, 12, 31, 0, 0), DK)
      val p4 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2016, 12, 12, 0, 0), EU)
      
      assert(mergePeriods(List(p1, p2, p3), ResidencePeriod.mergeOn, ResidencePeriod.mergeFunc) == List(p4, p3)) 
  }
  
  it should "split a period that spans two calendar years into two periods, each within a calendar year" in {
    val p1 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2017, 8, 8, 0, 0), EU)
    val p1a = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2017, 1, 1, 0, 0), EU)
    val p1b = new ResidencePeriod(new DateTime(2017, 1, 1, 0, 0), new DateTime(2017, 8, 8, 0, 0), EU)
    
    assert(alignRegistrationPeriodsWithYears(List(p1)) == List(p1a, p1b))
  }
  
  it should "split a period that spans multiple calendar years into multiple periods, each within a calendar year" in {
    val p1 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2019, 8, 8, 0, 0), EU)
    val p1a = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2017, 1, 1, 0, 0), EU)
    val p1b = new ResidencePeriod(new DateTime(2017, 1, 1, 0, 0), new DateTime(2018, 1, 1, 0, 0), EU)
    val p1c = new ResidencePeriod(new DateTime(2018, 1, 1, 0, 0), new DateTime(2019, 1, 1, 0, 0), EU)
    val p1d = new ResidencePeriod(new DateTime(2019, 1, 1, 0, 0), new DateTime(2019, 8, 8, 0, 0), EU)
    
    assert(alignRegistrationPeriodsWithYears(List(p1)) == List(p1a, p1b, p1c, p1d))
  }
  
  it should "keep periods within a year and split those that span more than one year, while keeping the total period" in {
    val p1 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2016, 6, 6, 0, 0), EU)
    val p2 = new ResidencePeriod(new DateTime(2016, 6, 7, 0, 0), new DateTime(2017, 8, 8, 0, 0), EU)
    val p3 = new ResidencePeriod(new DateTime(2017, 8, 9, 0, 0), new DateTime(2017, 10, 10, 0, 0), EU)
    val p2a = new ResidencePeriod(new DateTime(2016, 6, 7, 0, 0), new DateTime(2017, 1, 1, 0, 0), EU)
    val p2b = new ResidencePeriod(new DateTime(2017, 1, 1, 0, 0), new DateTime(2017, 8, 8, 0, 0), EU)
    
    assert(alignRegistrationPeriodsWithYears(List(p1, p2, p3)) == List(p1, p2a, p2b, p3))
  }
  
  it should "keep periods that are less than 8 years before the application date, and drop those more than 8 years old" in {
    val p0 = new ResidencePeriod(new DateTime(2007, 1, 1, 0, 0), new DateTime(2008, 1, 1, 0, 0), DK)
    val p1 = new ResidencePeriod(new DateTime(2008, 1, 1, 0, 0), new DateTime(2010, 1, 1, 0, 0), DK)
    val p2 = new ResidencePeriod(new DateTime(2010, 1, 1, 0, 0), new DateTime(2017, 1, 1, 0 ,0), DK)
    
    val p1a = new ResidencePeriod(new DateTime(2009, 1, 1, 0, 0), new DateTime(2010, 1, 1, 0, 0), DK)
    val p2a = new ResidencePeriod(new DateTime(2010, 1, 1, 0, 0), new DateTime(2017, 1, 1, 0 ,0), DK)
    
    assert(limitToRelevantPeriods(List(p0, p1, p2), new DateTime(2009, 1, 1, 0, 0)) == List(p1a, p2a)) 
  }
}