package streaming01.caolaoshi

import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.log4j.{Level, Logger}
import org.apache.spark.deploy.SparkSubmit
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}
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

object StreamingSQL {

  // 配置日志的显示级别
  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setMaster("local[*]") // master 需要 2 个核, 以防止饥饿情况（starvation scenario）.
      .setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)

    // 批次时间  为 2s  一个批次
    val ssc: StreamingContext = new StreamingContext(sc, Seconds(2))

    // 读取socket端口的数据，  防火墙   需要一个cores
    val sstext: ReceiverInputDStream[String] = ssc.socketTextStream("hdp-03", 9999)

    // 创建 SparkSession实例
    val spark: SparkSession = SparkSession.builder()
      .config(conf)
      .getOrCreate()
    import spark.implicits._

    val config = ConfigFactory.load()
    sstext.foreachRDD(rdd => {

      // SQL   DataFrame / Dataset  SQLContext /SparkSession
      val ds: Dataset[String] = spark.createDataset(rdd)
      val df: DataFrame = ds.flatMap(_.split(" ")).toDF("word")

      // sql  注册临时表
      df.createOrReplaceTempView("v_word")

      val result: DataFrame = spark.sql("select word ,count(*) cnts from v_word group by word order by cnts desc ")

//      result.show()

      val url  = config.getString("db.url")
      val conn = new Properties()
      conn.setProperty("user",config.getString("db.user"))
      conn.setProperty("password",config.getString("db.passwd"))
      conn.setProperty("driver",config.getString("db.driver"))

      result.write.mode(SaveMode.Append).jdbc(url,"streamingwc2",conn)
    })

    ssc.start() // 启动程序
    ssc.awaitTermination() // 阻塞程序 挂起
  }

}
