package day05

import utils.MySpark
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/21
  * Desc:
  * 展示量和点击量
  * id,关键词组合,是否展示，是否点击
  *
  * （id，keywords,imp,click）
  */

object ImpAndClick {

  def main(args: Array[String]): Unit = {

    val sc: SparkContext = MySpark(this.getClass.getSimpleName)

    // 读取数据
    val file: RDD[String] = sc.textFile("f:/mrdata/impclick.txt")
      file.foreach(println)   ///   1011,华语剧场|当代|类型,1,0
    file.map(t=>{
      val split = t.split(",")
    })




    sc.stop()
  }
}
