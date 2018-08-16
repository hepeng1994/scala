package streaming01.caolaoshi

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huge
  * 824203453@qq.com
  * DATE: 2018/6/29
  * Desc:
  * streaming版本的wordcount
  */

object StreamingWCForeachRDD {

  // 配置日志的显示级别
  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    .setMaster("local[*]")  // master 需要 2 个核, 以防止饥饿情况（starvation scenario）.
    .setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)

    // 批次时间  为 2s  一个批次
    val ssc: StreamingContext = new StreamingContext(sc,Seconds(2))

    // 读取socket端口的数据，  防火墙   需要一个cores
    val textStream: ReceiverInputDStream[String] = ssc.socketTextStream("hdp-03",9999)

    // 对DStream执行操作   处理业务逻辑的代码也需要有cores
    val result: DStream[(String, Int)] = textStream.flatMap(_.split(" +")).map((_,1)).reduceByKey(_+_)

    result.print()
    // DStream  =  rdd +  time
//    result.foreachRDD((rdd,time)=>{
//      // 数据  time
//      rdd.foreach(t=>(println(t,time)))
//    })

/*    textStream.foreachRDD(rdd=>{
      val a = 4  // 是在Driver端执行的
      val key: RDD[(String, Int)] = rdd.flatMap(_.split(" +")).map((_,1)).reduceByKey(_+_)
      // 肯定是在executor中执行的。
      key.foreach(t=>println(t))
    })*/



    ssc.start() // 启动程序
    ssc.awaitTermination()  // 阻塞程序 挂起
  }

}
