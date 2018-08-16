package day03

import java.net.URL

import utils.MySpark
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/20
  * Desc:  分学科的topK
  * 第二种解决方案：
  * 过滤数据   升级版的过滤
  */

object SubFacTeacher2 {

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


    // 提取出所有的学科数据
    val subjectArr: Array[String] = splitRdd.map(_._1._1).distinct().collect()


    // 可以做遍历    代码的耦合性高
    //    for (i <- Array("php", "javaee", "bigdata")) {

    for (i <- subjectArr) {
      // php  过滤
      val phpSubject = splitRdd.filter(t => i.equals(t._1._1))

      // 分组聚合
      val phpRes: Array[((String, String), Int)] = phpSubject.reduceByKey(_ + _).sortBy(-_._2).take(topN)

      phpRes.foreach(println)
    }

    sc.stop()
  }
}
