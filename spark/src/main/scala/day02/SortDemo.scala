package day02

import utils.MySpark
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/19
  * Desc: 
  */

object SortDemo {

  def main(args: Array[String]): Unit = {
    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 11), ("c", 123)))

    //    rdd1.sortBy(-_._2)
    val result1: RDD[(String, Int)] = rdd1.sortBy(_._2, false)

    //    rdd1.sortBy(_._2,false).collect().foreach(println)

    rdd1.sortByKey(false).collect().foreach(println)


    sc.stop()
  }
}
