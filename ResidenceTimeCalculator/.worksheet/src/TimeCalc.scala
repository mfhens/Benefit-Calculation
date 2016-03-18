import org.joda.time._

object TimeCalc {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(85); 
  println("Welcome to the Scala worksheet");$skip(47); 
  
  val d1 = new DateTime(2012, 12, 31, 0, 0);System.out.println("""d1  : org.joda.time.DateTime = """ + $show(d1 ));$skip(42); 
  val d2 = new DateTime(2013, 1, 1, 0, 0);System.out.println("""d2  : org.joda.time.DateTime = """ + $show(d2 ));$skip(33); 

	val dur = new Duration(d1, d2);System.out.println("""dur  : org.joda.time.Duration = """ + $show(dur ));$skip(23); val res$0 = 
	
	dur.getStandardDays;System.out.println("""res0: Long = """ + $show(res$0))}
    
}
