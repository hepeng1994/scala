package day03

import java.io

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

  def test() = {

    val str = "a,b,,,"
    val splits = str.split(",") // 2
    println(splits.size) // 2
    val sp = str.split(",", 5) // 是我们的给定数据str 有5个字段
    println(sp.size)

    // 按照字符串中，数据原有的字段进行切分
    val split1 = str.split(",", -1)
    println(s"split1=${split1.size}")

    // 通过参数指定切分的长度为2
    val split = str.split(",", 2)
    println(split.size)
    println(split(0)) // a
    println(split(1)) // b,,,


  }

  def main(args: Array[String]): Unit = {

    test()


    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    // 读取数据
    // u1 12 zs
    val aFile = sc.textFile("f:/mrdata/joindata/a.txt")
    // u1 2016 9 m1
    val bFile: RDD[String] = sc.textFile("f:/mrdata/joindata/b.txt")


    // 数据预处理  ---数据切分
    val aSplitRdd: RDD[(String, String)] = aFile.map(str => {
      val split = str.split(" ", 2)
      val id = split(0)
      val ageAndName = split(1)
      // 组成元组
      (id, ageAndName)
    })

    val bSplitRdd: RDD[(String, List[String])] = bFile.map(str => {
      val split = str.split(" ", 2)
      val id = split(0)
      val movie = split(1)
      (id, List(movie))
    })

    // 先对第二个数据进行进行处理  把相同key的数据聚合到一起
    val breduceRdd: RDD[(String, List[String])] = bSplitRdd.reduceByKey(_ ++ _)

    // 对两个数据集进行 join
    val abJoinRdd: RDD[(String, (String, Option[List[String]]))] = aSplitRdd.leftOuterJoin(breduceRdd)


    val result: RDD[(String, String)] = abJoinRdd.mapValues {
      case (ageAndName, movie) => {
        // 如果None  用 null null null
        val movieData: String = movie match {
          case None => "null null null"
          // Some (v) => v  排序 ---》  拼接成字符串
          // 按照年份的升序排序  --->   拼接成字符串
          case Some(lst) => lst.sortBy(_.split(" ")(0).toInt).mkString(" ")
        }
        // 拼接字符串
        ageAndName + "," + movieData
        ageAndName.concat(",").concat(movieData)
      }
    }
    // 整理展示的结果格式
    result.map { case (k, v) => k.concat(" ").concat(v) } foreach (println)


   

   // 都指定长度为2
    /*
        (u3,Iterable("2012 3 m5"))
        rddb.groupByKey()
        rdda.leftOuterJoin(rddb)
        rdda.cogroup(rddb)
        rdda.union(rddb).groupByKey*/


    // 数据的聚合  ***

    // 数据的整理输出

    sc.stop()

  }
}
