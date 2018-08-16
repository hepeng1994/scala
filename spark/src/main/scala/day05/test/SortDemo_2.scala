package day05.test

import org.apache.spark.rdd.RDD
import utils.MySpark

object SortDemo_2 {
    def main(args: Array[String]): Unit = {
        val sc = MySpark(this.getClass.getSimpleName)
        val products: RDD[String] = sc.makeRDD(List("pipian 99.9 1000", "lazhu 99.9 10000", "shoukao 299.9 10000", "feizao 3.9 1000", "shouji 4999.99 100"))
        // 按照商品库存的降序
        // 数据切分
        val splitRdd = products.map(t => {
            val split = t.split(" ")
            val pname = split(0)
            val price = split(1).toDouble
            val amount = split(2).toInt
            new MyProduct(pname, price, amount)
        })
        val result = splitRdd.sortBy(t=>t)
        result.foreach(println)
        sc.stop()
    }
}
class MyProduct(val pname: String, val price: Double, val amount: Int)extends Ordered[MyProduct]with Serializable{
    override def compare(that: MyProduct): Int = {
        if (this.price == that.price){
            //库存的升序
            this.amount-that.amount
        }else{
            //按照价格的升降
            if(that.price- this.price>0) 1 else  -1
        }
    }

    override def toString = s"MyProduct($pname, $price, $amount)"
}
