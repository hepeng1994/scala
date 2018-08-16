package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huge
  * Desc:
  * A B 集合数据 聚合
  * groupByKey
  * id  age name,year month movie      年份的升序排序 ，没有关联上的  用 “null”补全
  * （因为要用null补全，所以不能使用join）
  *
  * groupByKey方法实现，并非最优方案
  *
  */

object ABDataReduceByKey {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)
    // 获取到SparkContext的实例
    val sc: SparkContext = new SparkContext(conf)

    // 获取了两个数据集
    // u2 15 xx
    val aData = sc.textFile("f:/mrdata/joindata/a.txt")
    // u2 2017 12 m2
    val bData = sc.textFile("f:/mrdata/joindata/b.txt")

    // 数据集要切分,指定切分的数据长度为2
    val aSplitData = aData.map(t => {
      val split: Array[String] = t.split(" ", 2)
      val id = split(0)
      val ageAndName = split(1)
      (id, Iterable(ageAndName))
    })

    val bSplitData = bData.map(t => {
      val split = t.split(" ", 2)
      val id = split(0)
      val movie = split(1)
      (id, Iterable(movie))
    })

    // 先union，再使用groupByKey
    val unionData = aSplitData.union(bSplitData)

    val groupedData = unionData.reduceByKey(_++_)
    // a   b
    val result2 = groupedData.mapValues(it => {
      // head 和tail 会存在问题  集群模式下，不能保证 head元素就是a集合中的元素
      /* groupByKey 错误的示范
      val a = it.head
      val movie: Iterable[String] = it.tail
      // 如果b数据集没有相关的key
      val movieData: String = if (movie.isEmpty) {
        "null null null"
      } else {
        // 排序 迭代器啥你没有sortBy方法，把数据集转换成List进行排序
        // 2014 2 m4 2017 1 m3 切分，取第一个值，年份
        movie.toList.sortBy(t => t.split(" ")(0)).mkString(" ")
      }*/


      // 改成 find 和filter的方式来获取数据  根据切分之后的数组长度来判断
      val find: Option[String] = it.find(_.split(" ").length == 2)
      val fRes = find.get
      val right: Iterable[String] = it.filter(_.split(" ").length == 3)

      val rightRes =if(right.isEmpty){"null null null"}else{
        // 按照年份进行排序 并把集合  拼接成字符串
        right.toList.sortBy(_.split(" ")(0).toInt).mkString(" ")
      }
      // 把两个数据集的结果都拼接起来
      fRes.concat(",").concat(rightRes)
    })

    // 打印结果
    result2.foreach(println)

    // 释放资源
    sc.stop()
  }
}
