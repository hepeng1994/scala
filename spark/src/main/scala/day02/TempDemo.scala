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

object TempDemo {

  def main(args: Array[String]): Unit = {
    val d1 = Array(("bj", 28.1), ("sh", 28.7), ("gz", 32.0), ("sz", 33.1))
    val d2 = Array(("bj", 27.3), ("sh", 30.1), ("gz", 33.3))
    val d3 = Array(("bj", 28.2), ("sh", 29.1), ("gz", 32.0), ("sz", 30.5))


    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    val data = d1 ++ d2 union d3
    // 把集合转换成rdd
    val rdd1: RDD[(String, Double)] = sc.makeRDD(data)


    val grouped: RDD[(String, Iterable[Double])] = rdd1.groupByKey()

    // 统计平均温度 =  总的温度  /  月份数量
    val result: RDD[(String, Double)] = grouped.mapValues(it => {
      // 总的温度
      val totalTmp = it.sum
      val len = it.size
      totalTmp / len
    })

    result.distinct()

    // 打印结果
    result.foreach(println)

    sc.stop()
  }
}
