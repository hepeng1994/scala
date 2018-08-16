package sparksql

import java.util.Properties

import utils.IpUtils
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql._

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc: 
  */

object IpLocalConfigFactory {

  def main(args: Array[String]): Unit = {

    // 传统的通过类加载器获取某一个配置文件
    //    val asStream = IpLocal.getClass.getClassLoader.getResourceAsStream("application.conf")
    //    val prop = new Properties()
    //    prop.load(asStream)
    //    prop.getProperty("")


    // 默认的加载顺序 application.conf  -->  application.json --->  application.properties
    val config = ConfigFactory.load()
    //    val conf1 = config.getString("db.url")
    //    println(conf1)

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import spark.implicits._

    // 读取数据
    val ipruleDs: Dataset[String] = spark.read.textFile("f:/mrdata/ipdata/ip.txt")
    val logDs: Dataset[String] = spark.read.textFile("f:/mrdata/ipdata/ipaccess.log")

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
