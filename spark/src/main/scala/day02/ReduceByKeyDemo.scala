package day02


import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import utils.MySpark

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/19
  * Desc: 
  */

object ReduceByKeyDemo {

  def main(args: Array[String]): Unit = {

    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    val rdd1: RDD[Int] = sc.makeRDD(List(1, 4, 5, 6))
    // groupBy   RDD[K]  RDD[K,V]
    val groupedRdd: RDD[(Int, Iterable[Int])] = rdd1.groupBy(t => t)
    val groupedRdd2: RDD[(String, Iterable[Int])] = rdd1.groupBy(t => t.toString)
    println(groupedRdd.collect().toBuffer)
    // groupBy 返回值类型[(K,Iterable[T])]  K：是传入的函数的返回值类型  T  是rdd的元素类型


    val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("rb", 1000), ("baby", 990),
      ("yangmi", 980), ("bingbing", 5000), ("bingbing", 1000), ("baby", 2000)), 3)


    // 返回值的类型
    val rdd3: RDD[(String, Iterable[(String, Int)])] = rdd2.groupBy(_._1)

    val result1: RDD[(String, Int)] = rdd3.mapValues(_.map(_._2).sum)


    // groupByKey   RDD[K,V]  Iterable[990,2000]
    val rdd4: RDD[(String, Iterable[Int])] = rdd2.groupByKey()
    println(s"rdd4 part = ${rdd4.partitions.size}")

    val result3: RDD[(String, Int)] = rdd4.mapValues(_.sum)

    println(rdd2.groupByKey(10).partitions.size)

    // reduceByKey   RDD[K,V]

    val rdd6: RDD[(String, Int)] = rdd2.reduceByKey(_ + _)
    // 指定生成的rdd的分区的数量
    val rdd7: RDD[(String, Int)] = rdd2.reduceByKey(_ + _, 10)

    val rdd5: RDD[(String, List[Int])] = sc.makeRDD(List(("a", List(1, 3)), ("b", List(2, 4))))
    rdd5.reduceByKey(_ ++ _)

    sc.stop()
  }
}
