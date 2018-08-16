package day04

import utils.MySpark
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/20
  * Desc:
  * 数据A ；  id age name
  * 数据集B:  id year month movie
  *
  * 需求结果： id age name year month movie
  * 要求： 如果没有数据B null null null
  *
  * reduceByKey  + leftOuterJoin
  */

object ABTest {


  def main(args: Array[String]): Unit = {



    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    // 读取数据
    // u1 12 zs
    val aFile = sc.textFile("f:/mrdata/joindata/a.txt")
    // u1 2016 9 m1
    val bFile: RDD[String] = sc.textFile("f:/mrdata/joindata/b.txt")


    sc.stop()

  }
}
