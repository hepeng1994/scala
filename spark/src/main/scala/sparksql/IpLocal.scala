package sparksql

import java.util.Properties

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import utils.IpUtils

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc: 
  */

object IpLocal {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import spark.implicits._

    // 读取数据
    val ipruleDs: Dataset[String] = spark.read.textFile("E:\\x\\spark资料\\spark-04\\作业题\\ip.txt")
    val logDs: Dataset[String] = spark.read.textFile("E:\\x\\spark资料\\spark-04\\作业题\\ipaccess.log")

    // 数据切分
    val ipRules: Dataset[Row] = ipruleDs.map(str => {
      val split = str.split("\\|")
      (split(2).toLong, split(3).toLong, split(6))
    }).toDF("start", "end", "province")

    val longIps = logDs.map(str => {
      val split = str.split("\\|")
      val ip = split(1)
      IpUtils.ip2Long(ip)
    }).toDF("longIp")


    // 数据匹配聚合  关联查询
    ipRules.createTempView("v_ipRules")

    longIps.createTempView("v_longIp")

    val result: DataFrame = spark.sql("select province,count(*) as cnts from v_ipRules t1 join " +
      "v_longIp t2 on t2.longIp between t1.start and t1.end " +
      "group by province " +
      " order by cnts desc ")
    result.show()

    result.printSchema()

    // 写入mysql
    val url = "jdbc:mysql://localhost:3306/day02?characterEncoding=utf-8"
    val table = "access_log10"
    val conn = new Properties()
    conn.setProperty("user", "root")
    conn.setProperty("password", "root")
    conn.setProperty("driver", "com.mysql.jdbc.Driver")

    result.write.jdbc(url, table, conn)

    // 高内聚 低耦合

    // 多表join   ------》   map 端的join

    spark.stop()

  }
}
