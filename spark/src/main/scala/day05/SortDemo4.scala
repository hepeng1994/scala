package day05

import utils.MySpark
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/23
  * Desc:
  * 自定义排序 利用隐式转换
  */

object SortDemo4 {

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

    // 利用隐式转换   隐式方法
    implicit def pro2Ordered(pro: MyProducts2): Ordered[MyProducts2] = {
      new Ordered[MyProducts2] {
        override def compare(that: MyProducts2): Int = {
          if (pro.price == that.price) {
            // 库存的升序
            pro.amount - that.amount
          } else {
            // 按照价格的降序
            if (that.price - pro.price > 0) 1 else -1
          }
        }
      }
    }

    // 仅仅是利用类的排序规则
    val result: RDD[(String, Double, Int)] = splitRdd.sortBy(t => MyProducts2(t._1, t._2, t._3))
    result.foreach(println)

    sc.stop()
  }
}

case class MyProducts2(val pname: String, val price: Double, val amount: Int) {

  override def toString = s"MyProducts2($pname, $price, $amount)"
}