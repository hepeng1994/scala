package day05

import utils.MySpark
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/23
  * Desc: 
  */

object PersistDemo {

  def main(args: Array[String]): Unit = {

    val sc = MySpark(this.getClass.getSimpleName)

    val products: RDD[String] = sc.makeRDD(List("pipian 99.9 1000", "lazhu 3.5 10000", "shoukao 299.9 10000", "feizao 3.9 1000", "shouji 4999.99 100"))

    // 直接在rdd后面调用方法，参数传递具体的存储级别。
    products.cache()
    products.persist(StorageLevel.MEMORY_ONLY_SER)



    sc.stop()
  }
}
