package streaming01.caolaoshi

import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huge
  * 824203453@qq.com
  * DATE: 2018/6/29
  * Desc:
  * 带历史状态统计的wordcount
  */

object UpDataStateByKeyDemo {

  // 配置日志的显示级别
  Logger.getLogger("org").setLevel(Level.ERROR)


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)

    // 批次时间  为 2s  一个批次
    val ssc: StreamingContext = new StreamingContext(sc, Seconds(2))

    // 在StreamingContext 上设置checkpoint
    ssc.checkpoint("ck-streaming")

    // fs.defaultFS:file:///


    // 读取socket端口的数据，  防火墙   需要一个cores
    val sstext: ReceiverInputDStream[String] = ssc.socketTextStream("hdp-03", 9999)


    // 换一个API
    /**
      * def updateStateByKey[S: ClassTag](
      * updateFunc: (Seq[V], Option[S]) => Option[S]
      * 当前批次的数据  Seq[V]   wc 1 1 1 1 1 1
      * w 10  c 20
      * 历史累加的数据  Some(V)  None
      */

    // 函数的返回值类型是Option[Int]
    val updateFunc = (currentData: Seq[Int], totalData: Option[Int]) => {
      // 把返回值类型封装成Some
      Some(currentData.sum + totalData.getOrElse(0))
    }


    val tpDS: DStream[(String, Int)] = sstext.flatMap(_.split(" ")).map((_, 1))

    // 带历史状态的wc
    val stateWC: DStream[(String, Int)] = tpDS.updateStateByKey(updateFunc)

    stateWC.print()


    ssc.start() // 启动程序
    ssc.awaitTermination() // 阻塞程序 挂起
  }

}
