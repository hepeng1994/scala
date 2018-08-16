package day03

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * Created by Huge
  * Desc:
  * 两个数据集的聚合
  * reduceByKey
  * groupByKey    union
  * cogroup
  * leftOuterJoin
  *
  * 根据key来关联？  join
  * A:  id name  age
  * B:  id year  month  movie
  *
  * 关联不上的，用null补全
  *
  */

object ABTestCogroup {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)
    // 就是要对两个数据进行关联
    // 读取2个文件数据
    val aData: RDD[String] = sc.textFile("F:\\mrdata\\joindata/a.txt")
    val bData = sc.textFile("F:\\mrdata\\joindata/b.txt")

    // 对A数据进行切分
    val aSplitedData = aData.map(t => {
      // 指定数据的长度为2
      val splits: Array[String] = t.split(" ", 2)
      val id = splits(0)
      val nameAndAge = splits(1)
      // 封装成元组 ，然后返回
      (id, nameAndAge)
    })

    // 对B数据进行切分
    val bSplitedData = bData.map(t => {
      val spliteds = t.split(" ", 2)
      val id = spliteds(0)
      val movie = spliteds(1)
      // 数据组装成元组
      (id, movie)
    })

    // 数据的聚合  // 根据key进行分组聚合
    val cogroupData: RDD[(String, (Iterable[String], Iterable[String]))] = aSplitedData.cogroup(bSplitedData)

    val result: RDD[(String, String)] = cogroupData.mapValues(tp => {
      // 第一个迭代器是 nameAndAge的数据  第二个 迭代器是  movie
      val nameIt: Iterable[String] = tp._1
      val movieIt = tp._2
      // 1 A 数据集中， 长度只有1
      val nameAndAge: String = nameIt.mkString // nameIt.head


      // 2 对movie 迭代器数据进行处理
      val bMovieData: String = if (movieIt.isEmpty) {
        // 赋予默认值
        "null null null"
      } else { // 如果有数据，就要进行排序   把迭代器转换成list 在调用sortBy方法
        // 2017 12 m2
        val lst: List[String] = movieIt.toList.sortBy(t => t.split(" ")(0).toInt)
        // 按照指定的分隔符  把 数据拼接起来
        lst.mkString(" ")
      }

      // 把两个处理之后的数据集进行拼接
      nameAndAge.concat(",").concat(bMovieData)
    })

    // 打印结果数据
    result.foreach(println)

    sc.stop()
  }
}
