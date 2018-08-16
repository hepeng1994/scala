package streaming01.caolaoshi

import java.sql.{DriverManager, PreparedStatement, ResultSet}
import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huge
  * 824203453@qq.com
  * DATE: 2018/6/29
  * Desc:
  * 带历史状态统计的wordcount
  * 利用mysql存储带历史状态的值
  * 利用sparksql的语法
  */

object UpDataStateSQLMySql {

  // 配置日志的显示级别
  Logger.getLogger("org").setLevel(Level.ERROR)


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)

    // 批次时间  为 2s  一个批次
    val ssc: StreamingContext = new StreamingContext(sc, Seconds(2))

    // 读取socket端口的数据，  防火墙   需要一个cores
    val sstext: ReceiverInputDStream[String] = ssc.socketTextStream("hdp-03", 9999)

    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._

    val config = ConfigFactory.load()

    sstext.foreachRDD(rdd => {

      // 当前批次的数据
      val wordDF = spark.createDataset(rdd).flatMap(_.split(" ")).toDF("word")
      val wordAndCount: DataFrame = wordDF.groupBy($"word").count().toDF("word", "cnts")

//      wordAndCount.printSchema()
      /**
        * root
        * |-- word: string (nullable = true)
        * |-- cnts: long (nullable = false)
        */

      println("----------------")
      // 查询历史数据  假定表已存在。
      val url = config.getString("db.url")
      val conn = new Properties()
      conn.setProperty("user", config.getString("db.user"))
      conn.setProperty("password", config.getString("db.passwd"))
      conn.setProperty("driver", config.getString("db.driver"))
      val hisDF: DataFrame = spark.read.jdbc(url, "streamingword", conn)
//      hisDF.printSchema()
      /**
        * root
        * |-- word: string (nullable = true)
        * |-- cnts: integer (nullable = true)
        */

        import org.apache.spark.sql.functions._
      val result: DataFrame = wordAndCount.union(hisDF).groupBy($"word").agg(sum($"cnts") as "cnts")
//      result.printSchema()
      result.show()
      if(!result.rdd.isEmpty()){
      result.write.mode(SaveMode.Overwrite).jdbc(url,"streamingword",conn)
      }
    })


    ssc.start() // 启动程序
    ssc.awaitTermination() // 阻塞程序 挂起
  }

}
