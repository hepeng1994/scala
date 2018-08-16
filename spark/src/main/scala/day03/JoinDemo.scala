package day03

import utils.MySpark
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/20
  * Desc: 
  */

object JoinDemo {

  def main(args: Array[String]): Unit = {
    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    // K - V
    val rdd1: RDD[(String, Double)] = sc.makeRDD(List(("reba", 9000.0), ("nazha", 8000.0), ("ruhua", 10000.0)))

    // K - W
    val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("reba", 7), ("nazha", 8), ("yangmi", 3)))

    // 默认分区数不变
    val join: RDD[(String, (Double, Int))] = rdd1.join(rdd2)

    //    join.foreach(println)

    //    RDD[(K, (V, Option[W]))]    右边可能关联不上
    val result1: RDD[(String, (Double, Option[Int]))] = rdd1.leftOuterJoin(rdd2)
    // 求当月的出场费总额
    result1.mapValues(tp => tp._2.getOrElse(0) * tp._1)


//      .foreach(println)


    println("----------------------")
    //    RDD[(K,(Option[V],W))]
    rdd1.rightOuterJoin(rdd2)
      //.foreach(println)


    // cogroup

    val cogroup: RDD[(String, (Iterable[Double], Iterable[Int]))] = rdd1.cogroup(rdd2)

//    cogroup.foreach(println)

    cogroup.mapValues(tp=>{
      tp._1.sum * tp._2.sum
    }).foreach(println)



    sc.stop()
  }
}
