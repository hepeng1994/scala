package streaming01.caolaoshi

import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/29
  * Desc: 
  */

object DStreamTans {
  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)

    // 批次时间  为 2s  一个批次
    val ssc: StreamingContext = new StreamingContext(sc, Seconds(2))
    val textStream: ReceiverInputDStream[String] = ssc.socketTextStream("hdp-03",9999)

    val flatDS: DStream[(String,Int)] = textStream.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)

    // key的数量
//    val count: DStream[Long] = flatDS.count()
//    count.print()

//    val value:DStream[((String,Int),Long)] = flatDS.countByValue()
//    value.print()

    // transform  RDD  --->  RDD的转换
    /*val transform: DStream[String] = flatDS.transform(rdd => {
      rdd.map(_._1)
    })*/


//    flatDS.saveAsTextFiles("out","dir")

    ssc.start()
    ssc.awaitTermination()
  }
}
