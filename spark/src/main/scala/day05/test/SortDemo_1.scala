package day05.test

import utils.MySpark
import org.apache.spark.rdd.RDD
object SortDemo_1 {
    def main(args: Array[String]): Unit = {
        val sc = MySpark(this.getClass.getSimpleName)
        val product = sc.makeRDD(List("pipian 99.9 1000","lazhu 3.5 10000","shoukao 299.9 10000","feizao 3.9 1000","shouji 4999.99 100"))
        //按照商品库存的降序
        //数据切分
        val splitRdd = product.map(t => {
            val split = t.split(" ")
            val pname = split(0)
            val price = split(1).toDouble
            val amount = split(2).toInt
            (pname, price, amount)
        })
        //排序
        val result = splitRdd.sortBy(- _._3)
        // 按照 商品的价格降序，如果价格相同，按照库存的升序排序
        result.coalesce(1).foreach(println)
        sc.stop()
    }
}

