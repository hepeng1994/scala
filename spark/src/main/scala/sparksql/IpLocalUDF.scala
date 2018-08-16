package sparksql

import java.util.Properties

import utils.IpUtils
import com.typesafe.config.ConfigFactory
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql._

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc:
  * UDF：
  */

object IpLocalUDF {

  def main(args: Array[String]): Unit = {

    // 默认的加载顺序 application.conf  -->  application.json --->  application.properties
    val config = ConfigFactory.load()

    val spark: SparkSession = SparkSession.builder()
//      .master("local[*]")
//      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import spark.implicits._

    // 读取数据
    val ipruleDs: Dataset[String] = spark.read.textFile("f:/mrdata/ipdata/ip.txt")
    val logDs: Dataset[String] = spark.read.textFile("f:/mrdata/ipdata/ipaccess.log")

    // 数据切分
    val ipRules: Dataset[(Long, Long, String)] = ipruleDs.map(str => {
      val split = str.split("\\|")
      (split(2).toLong, split(3).toLong, split(6))
    })

    // 可以 先把收集收集起来  ds --> Array
    //    ipRules.rdd.collect()  DataFrame .collect  --->  Array[Row]
    val ipRulesArr: Array[(Long, Long, String)] = ipRules.collect()

    // 把ip规则库数据进行广播
    val bc: Broadcast[Array[(Long, Long, String)]] = spark.sparkContext.broadcast(ipRulesArr)

    val longIps = logDs.map(str => {
      val split = str.split("\\|")
      val ip = split(1)
      IpUtils.ip2Long(ip)
    }).toDF("longIp")

    longIps.createTempView("v_longIp")

    // 自定义函数的名称  UDF 必须先定义再使用
    spark.udf.register("ip2Province", (ip: Long) => {
      val newIpRules: Array[(Long, Long, String)] = bc.value
      // 根据10 进制的ip地址，得到省份
      IpUtils.binarySearch(ip,newIpRules)
    })
    // 自定义函数
    val result = spark.sql("select ip2Province(longIp) province,count(*) cnts from v_longIp where ip2Province(longIp) != 'unknown' " +
      "group by province" +
      " order by cnts desc")

    result.printSchema()

    // 写入mysql
    val url = config.getString("db.url")
    val table = config.getString("db.table")
    val conn = new Properties()
    // user是关键字 ，不能用于key
    conn.setProperty("user", config.getString("db.user"))
    conn.setProperty("password", config.getString("db.passwd"))
    conn.setProperty("driver", config.getString("db.driver"))

    result.write.mode(SaveMode.Overwrite).jdbc(url, table, conn)
    //    result.write.mode("append").jdbc(url, table, conn)

    // 高内聚 低耦合

    // 多表join   ------》   map 端的join
    spark.stop()

  }
}
