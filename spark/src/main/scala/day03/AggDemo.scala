package day03

import utils.MySpark
import org.apache.spark.SparkContext

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/20
  * Desc: 
  */

object AggDemo {

  def main(args: Array[String]): Unit = {
    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    val rdd1 = sc.makeRDD(List(1,3,4,5),2)


    sc.stop()
  }
}
