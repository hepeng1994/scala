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
  * streaming版本的wordcount
  */

object StreamingWordCount {

  // 配置日志的显示级别
//  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    .setMaster("local[*]")  // master 需要 2 个核, 以防止饥饿情况（starvation scenario）.
    .setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)

    // 批次时间  为 2s  一个批次
    val ssc: StreamingContext = new StreamingContext(sc,Seconds(2))


    // 读取socket端口的数据，  防火墙   需要一个cores
    val textStream: ReceiverInputDStream[String] = ssc.socketTextStream("hdp-03",9999)


    // 占用一个cores
//    val textStream2: ReceiverInputDStream[String] = ssc.socketTextStream("hdp-04",9999)

    // 对DStream执行操作   处理业务逻辑的代码也需要有cores
    val result: DStream[(String, Int)] = textStream.flatMap(_.split(" +")).map((_,1)).reduceByKey(_+_)

    // 调用action算子
    result.print() // 默认打印10行

    //  sparkstreaming 程序的必备
    ssc.start() // 启动程序
    ssc.awaitTermination()  // 阻塞程序 挂起
  }

}
