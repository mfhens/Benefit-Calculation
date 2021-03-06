package org.kombit.calculators.integrationbenefit

import org.kombit.core.model._
import org.joda.time._
import org.scalatest._

class ResidenceTimeCalculatorSpec extends org.kombit.core.UnitSpec {
  
 "The ResidenceTimeCalculator" should "return a date that is 2556 days after the application date, if the applicant has applied on his first day in Denmark" in {
    val p0 = new ResidencePeriod(new DateTime(2016, 1, 1, 0, 0), new DateTime(2016, 1, 1, 0, 0), DK)
    
    val calc = new ResidenceTimeCalculator(List(p0))
    
    assert(calc.entitlementDateTime == p0.endDate.plusDays(2556))
  }
 
  it should "not count foreign stays of less than two months" in {
    
    val p0 = new ResidencePeriod(new DateTime(2015, 2, 1, 0, 0), new DateTime(2016, 1, 1, 0, 0), DK)
    val p1 = new ResidencePeriod(new DateTime(2014, 10, 1, 0, 0), new DateTime(2015, 2, 1, 0, 0), Other)
        
    val calc = new ResidenceTimeCalculator(List(p1, p0))
    
    assert(calc.entitlementDateTime == p0.endDate.plusDays(2190))
  }
  
  it should "count foreign stays of two consecutive months regardless of number of days" in {
    val p0 = new ResidencePeriod(new DateTime(2016, 3, 1, 0, 0), new DateTime(2016, 3, 1, 0, 0), DK)
    val p1 = new ResidencePeriod(new DateTime(2016, 1, 1, 0, 0), new DateTime(2016, 3, 1, 0, 0), Other)
    
    val calc = new ResidenceTimeCalculator(List(p1, p0))
    assert(calc.entitlementDateTime == p0.endDate.plusDays(2556))
  }
}