package streaming01.caolaoshi

import java.sql.{DriverManager, PreparedStatement, ResultSet}

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.ConfigFile
import com.typesafe.config.ConfigFactory
import org.apache.log4j.{Level, Logger}
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
  */

object UpDataStateByKeyMySql {

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

    val batchData: DStream[(String, Int)] = sstext.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    val config = ConfigFactory.load()
    batchData.foreachRDD(rdd => {
      // rdd 有数据，
      if (!rdd.isEmpty()) {
        rdd.foreachPartition(it => {
          // 每一个分区 共用一个mysql连接  此处省略了try catch finally
          val conn = DriverManager.getConnection(config.getString("db.url"), config.getString("db.user"), config.getString("db.passwd"))

          val pstm1: PreparedStatement = conn.prepareStatement("create table if not exists streamingword (word varchar(20), cnts int)")
          pstm1.execute()
          //数据的迭代
          it.foreach(word => {
            // (String,Int) // 当前批次的数据
            // 去查询数据库  word 是否存在
            val pstm2: PreparedStatement = conn.prepareStatement("select * from streamingword where  word = ? ")
            // 赋值
            pstm2.setString(1, word._1)
            // 查询
            val query: ResultSet = pstm2.executeQuery()

            // 存在 取值 + 相加    update
            if (query.next()) {
              // 数据库中存储的key的历史值
              val oldValues: Int = query.getInt("cnts")

              // 当前的值 +  历史的值 == 需要存储的总的值
              val currentValues = oldValues + word._2
              val pstm3 = conn.prepareStatement("update streamingword set cnts = ? where word = ? ")
              pstm3.setInt(1, currentValues)
              pstm3.setString(2, word._1)
              pstm3.execute()
            } else { // 不存在  直接写
              val pstm4 = conn.prepareStatement("insert into streamingword values (?,?)")
              pstm4.setString(1, word._1)
              pstm4.setInt(2, word._2)
              pstm4.execute()
            }
          })
          conn.close()
        })
      }
    })

    ssc.start() // 启动程序
    ssc.awaitTermination() // 阻塞程序 挂起
  }

}
