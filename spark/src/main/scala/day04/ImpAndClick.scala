package day04

import utils.MySpark
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/21
  * Desc:
  * 展示量和点击量
  * id,关键词组合,是否展示，是否点击
  *
  * （id，keywords,imp,click）
  */

object ImpAndClick {

  def main(args: Array[String]): Unit = {

    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    // 读取数据
    val file: RDD[String] = sc.textFile("f:/mrdata/impclick.txt")
    // 数据的切分  flatMap
    val splitRdd: RDD[((String, String), (Int, Int))] = file.flatMap(str => {
      val split = str.split(",")
      val id = split(0)
      // 关键词
      val keywords: String = split(1)
      val imp = split(2).toInt
      val click = split(3).toInt

      // 对关键词组合进行切分  需要转义
      val splitkeys: Array[String] = keywords.split("\\|")

      val result: Array[((String, String), (Int, Int))] = splitkeys.map(keyword => {
        // 组装成 嵌套元组
        ((id, keyword), (imp, click))
      })
      result
    })

    // 分组聚合
    val result: RDD[((String, String), (Int, Int))] = splitRdd.reduceByKey((a, b) => {
      val totalImp = a._1 + b._1
      val totalClick = a._2 + b._2
      (totalImp, totalClick)
    })

    // 结果展示格式的整理
    result.map{
      case ((id,keyword),(imp,click))=> (id,keyword,imp,click)
    }.foreach(println)

    //


    sc.stop()
  }
}
