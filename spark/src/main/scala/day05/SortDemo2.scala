package day05

import utils.MySpark
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/23
  * Desc:
  * 自定义排序 利用类来封装数据
  */

object SortDemo2 {

  def main(args: Array[String]): Unit = {
    val sc = MySpark(this.getClass.getSimpleName)

    val products: RDD[String] = sc.makeRDD(List("pipian 99.9 1000", "lazhu 3.5 10000", "shoukao 299.9 10000", "feizao 3.9 1000", "shouji 4999.99 100"))


    // 按照商品库存的降序

    // 数据切分
    val splitRdd: RDD[MyProducts] = products.map(t => {
      val split = t.split(" ")
      val pname = split(0)
      val price = split(1).toDouble
      val amount = split(2).toInt
      new MyProducts(pname, price, amount)
    })

    val result: RDD[MyProducts] = splitRdd.sortBy(t => t)

    result.foreach(println)

    sc.stop()
  }
}

// 类实现特质
case class MyProducts(val pname: String, val price: Double,val amount: Int) extends Ordered[MyProducts] /*with Serializable*/{
  // 具有了比较的规则
  override def compare(that: MyProducts): Int = {

    if (this.price == that.price) {
      // 库存的升序
      this.amount - that.amount
    } else {
      // 按照价格的降序
      if (that.price - this.price > 0) 1 else -1
    }
  }

  override def toString = s"MyProducts($pname, $price, $amount)"
}