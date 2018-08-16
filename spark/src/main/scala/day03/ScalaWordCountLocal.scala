package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huge
  * DATE: 2018/6/14
  * Desc:
  * spark scala 语法的wordcount
  */

object ScalaWordCountLocal {

  def main(args: Array[String]): Unit = {

    if (args.length != 2) {
      println("Usage :cn.huge.spark33.day01.ScalaWordCount <input> <output>")
      sys.exit(1)
    }

    // 参数接收
    val Array(input, output) = args

    val conf: SparkConf = new SparkConf()

//    conf.setMaster("local[3]") // 2 个cores
//    conf.setAppName(this.getClass.getSimpleName)



    // 创建SparkContext
    val sc: SparkContext = new SparkContext(conf)
    // 读取数据
    val file: RDD[String] = sc.textFile(input) // 这里有2个RDD
    // 切分并压平
    val words: RDD[String] = file.flatMap(_.split(" "))  // 1 ge
    // 组装
    val wordAndOne: RDD[(String, Int)] = words.map((_, 1)) // 1个
    // 分组聚合
    val result: RDD[(String, Int)] = wordAndOne.reduceByKey(_ + _) // ShuffledRDD
    // 直接存储到hdfs中
//    result.saveAsTextFile(output) // MapPartitionsRDD

    // 可以查看RDD的依赖关系
//    println(result.toDebugString)
    // 释放资源
    sc.stop()

  }
}
