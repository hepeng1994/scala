package day02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/19
  * Desc:
  * map
  * mapValues
  * mapPartitions
  */

object MapDemos {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)

    val rdd1:RDD[Int] = sc.makeRDD(List(1, 4, 2, 5, 7, 8), 3)

    // zip  拉链操作
    // zipWithIndex  索引   从0 开始
    val index: RDD[(Int, Long)] = rdd1.zipWithIndex()
    index.mapValues(t => t)

    // mapPartitions
    val rdd2: RDD[Int] = rdd1.mapPartitions(t => {
      t.map(_ * 10)
    })
    // 定义一个函数，返回rdd中的数据，以及对应的分区编号
    val f=(i:Int,it:Iterator[Int])=> {
      it.map(t=> s"p=$i,v=$t")
    }
    val rdd3: RDD[String] = rdd1.mapPartitionsWithIndex(f)

    println(rdd3.collect().toBuffer)
  }
}
