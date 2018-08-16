package utils

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/19
  * Desc: 
  */

object MySpark {

  // 获取local模式下的sc
  def apply(appName: String) = {
    val conf = new SparkConf()
    //去掉下边两条就不是本地了
    conf.setMaster("local[*]")
      .setAppName(appName)
    new SparkContext(conf)
  }
}
