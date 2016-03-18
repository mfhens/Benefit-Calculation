package org.kombit.core.model

object Playground {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(94); 
  println("Welcome to the Scala worksheet")

	import org.joda.time._;$skip(72); 
	  
	val d1 = new DateTime(2016, 12, 31, 0, 0);System.out.println("""d1  : org.joda.time.DateTime = """ + $show(d1 ));$skip(43); 
	val d2 = new DateTime(2015, 12, 31, 0, 0);System.out.println("""d2  : org.joda.time.DateTime = """ + $show(d2 ));$skip(43); 
	val d3 = new DateTime(2017, 12, 31, 0, 0);System.out.println("""d3  : org.joda.time.DateTime = """ + $show(d3 ));$skip(66); val res$0 = 
	List(d1, d2, d3).reduceLeft((l,r) => if (l isBefore r) l else r);System.out.println("""res0: org.joda.time.DateTime = """ + $show(res$0))}
}
