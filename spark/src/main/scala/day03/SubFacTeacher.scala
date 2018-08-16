package day03

import java.net.URL

import utils.MySpark
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/20
  * Desc:  分学科的topK
  * 第一种解决方案：
  * 聚合好的数据之上，再分组
  */

object SubFacTeacher {

  // 取Top3
  val topN = 2

  def main(args: Array[String]): Unit = {
    val sc = MySpark(this.getClass.getSimpleName)

    // 1,读数据
    val file: RDD[String] = sc.textFile("f:/mrdata/teacher.log")

    // 2，数据预处理  提取出 学科名称 teachername
    val splitRdd: RDD[((String, String), Int)] = file.map(str => {
      // 协议 host:port  URL
      //      http://bigdata.edu360.cn/laozhang
      val index: Int = str.lastIndexOf("/")

      // 获取老师的名称
      val tName = str.substring(index + 1)

      val url: URL = new URL(str.substring(0, index))
      // 获取hostName
      val host = url.getHost() // bigdata.edu360.cn
      // 分隔符需要进行转义
      val split = host.split("\\.")
      // 获取到学科名称
      val subject = split(0)

      // 把数据组装成嵌套元组
      ((subject, tName), 1)
    })


    // 分组聚合
    val result: RDD[((String, String), Int)] = splitRdd.reduceByKey(_ + _)

    // 再根据学科进行分组 ,之后，进行组内聚合
    val groupRdd: RDD[(String, Iterable[((String, String), Int)])] = result.groupBy(_._1._1)

    val result2: RDD[(String, List[((String, String), Int)])] = groupRdd.mapValues(it => {
      // 这里的sortBy 是本地集合的API
      it.toList.sortBy(-_._2).take(topN)
    })

    val result3 = result2.mapValues(lst => {

      lst.map { case ((_, tName), cnts) => (tName, cnts) }
    })
    result3.foreach(println)


    sc.stop()
  }
}
