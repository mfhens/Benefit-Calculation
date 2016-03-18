import org.joda.time._

object TimeCalc {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val d1 = new DateTime(2012, 12, 31, 0, 0)       //> d1  : org.joda.time.DateTime = 2012-12-31T00:00:00.000+01:00
  val d2 = new DateTime(2013, 1, 1, 0, 0)         //> d2  : org.joda.time.DateTime = 2013-01-01T00:00:00.000+01:00

	val dur = new Duration(d1, d2)            //> dur  : org.joda.time.Duration = PT86400S
	
	dur.getStandardDays                       //> res0: Long = 1
    
}