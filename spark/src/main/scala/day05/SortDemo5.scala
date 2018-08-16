package day05

import utils.MySpark
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/23
  * Desc:
  * 自定义排序 利用Ordering 的on方法
  */

object SortDemo5 {

  def main(args: Array[String]): Unit = {
    val sc = MySpark(this.getClass.getSimpleName)

    val products: RDD[String] = sc.makeRDD(List("pipian 99.9 1000", "lazhu 3.5 10000", "shoukao 299.9 10000", "feizao 3.9 1000", "shouji 4999.99 100"))


    // 按照商品库存的降序

    // 数据切分 数据还是元组
    val splitRdd = products.map(t => {
      val split = t.split(" ")
      val pname = split(0)
      val price = split(1).toDouble
      val amount = split(2).toInt
      (pname, price, amount)
    })


    /* t => (-t._2, t._3)  排序的条件
    (String, Double, Int)  数据的类型
    (Double, Int)          排序条件的类型
         */
    implicit val ord = Ordering[(Double, Int)].on[(String, Double, Int)](t => (-t._2, t._3))

    // 仅仅是利用类的排序规则
    val result: RDD[(String, Double, Int)] = splitRdd.sortBy(t => t)
    result.foreach(println)

    sc.stop()
  }
}

