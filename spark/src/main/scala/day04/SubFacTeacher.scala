package day04

import java.net.URL

import utils.MySpark
import org.apache.spark.Partitioner
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

    val subjectArr: Array[String] = splitRdd.map(_._1._1).distinct().collect()

    // 分组聚合    2次数据的重新分发
    //    val result: RDD[((String, String), Int)] = splitRdd.reduceByKey(_ + _)
    //    // 把相同学科的数据分到一个分区中   +  mapPartitions
    //    val parRes: RDD[((String, String), Int)] = result.partitionBy(MyPartitioner(subjectArr))


    // 优化的自定义分区器   只有一次数据的shuffle
    val parRes: RDD[((String, String), Int)] = splitRdd.reduceByKey(MyPartitioner(subjectArr), _ + _)

    val finalRes = parRes.mapPartitions(tp => {
//      tp   ((String, String), Int)
      // 要求返回值类型是Iterator
      tp.toList.sortBy(-_._2).take(topN).iterator
    })

    // 思考题？  TreeMap来实现   存储的数据是有序的（按照次数做降序）  只存储 topN条数据



    finalRes.foreach(println)

    sc.stop()
  }
}

object MyPartitioner {
  // 定义一个apply方法
  def apply(subject: Array[String]): MyPartitioner = new MyPartitioner(subject)
}

// 自定义类 分区器
class MyPartitioner(subject: Array[String]) extends Partitioner {

  // 直接把数组转换为Map  存储的 学科  --  分区编号
  val subMap: Map[String, Int] = subject.zipWithIndex.toMap

  // 分区的数量 = 学科的数量
  override def numPartitions: Int = subject.length

  // 根据key来获取分区的编号   实际上  学科的名称 --》 分区编号
  override def getPartition(key: Any): Int = {

    // 这里的key是什么类型？
    val (subject, _) = key.asInstanceOf[(String, String)]
    // asInstanceOf 类型强转       (类型) 变量
    // isInstanceOf  类型判断       instanceOf

    // 拿学科名称 去获取一个对应的分区编号
    subMap(subject)
  }
}
