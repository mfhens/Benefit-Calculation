package org.kombit.core.model

object Playground {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

	import org.joda.time._
	  
	val d1 = new DateTime(2016, 12, 31, 0, 0) //> d1  : org.joda.time.DateTime = 2016-12-31T00:00:00.000+01:00
	val d2 = new DateTime(2015, 12, 31, 0, 0) //> d2  : org.joda.time.DateTime = 2015-12-31T00:00:00.000+01:00
	val d3 = new DateTime(2017, 12, 31, 0, 0) //> d3  : org.joda.time.DateTime = 2017-12-31T00:00:00.000+01:00
	List(d1, d2, d3).reduceLeft((l,r) => if (l isBefore r) l else r)
                                                  //> res0: org.joda.time.DateTime = 2015-12-31T00:00:00.000+01:00
}