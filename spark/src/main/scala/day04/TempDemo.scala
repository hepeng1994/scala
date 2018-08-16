package day04

import utils.MySpark

import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/19
  * Desc: 
  */

object TempDemo {

  def main(args: Array[String]): Unit = {
    val d1 = Array(("bj", 28.1), ("sh", 28.7), ("gz", 32.0), ("sz", 33.1))
    val d2 = Array(("bj", 27.3), ("sh", 30.1), ("gz", 33.3))
    val d3 = Array(("bj", 28.2), ("sh", 29.1), ("gz", 32.0), ("sz", 30.5))
    //三个数组合并成一个数组
    val lines: Array[(String, Double)] = d1 ++ d2 ++ d3
    val sc = MySpark(this.getClass.getSimpleName)
    val fRdd: RDD[(String, Double)] = sc.makeRDD(lines)
    val keys: RDD[(String, Iterable[Double])] = fRdd.groupByKey()
    val res: RDD[(String, Double)] = keys.mapValues(x => {
      val sum = x.sum
      val len = x.size
      (sum / len)
    })
    res.foreach(println)

  }
}