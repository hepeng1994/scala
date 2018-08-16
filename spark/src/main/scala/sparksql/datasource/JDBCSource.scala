package sparksql.datasource

import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql._

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc: 
  */

object JDBCSource {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
    .getOrCreate()

    import spark.implicits._

    val config = ConfigFactory.load()

    // 读取mysql数据库  ---》  操作 之后 ---》 写到mysql中

    val url = config.getString("db.url")
    val conn = new Properties()
    conn.setProperty("user", config.getString("db.user"))
    conn.setProperty("password", config.getString("db.passwd"))

    // 连接mysql数据库 设置参数url driver dbtable user password
    val empData: DataFrame = spark.read.format("jdbc").options(
      Map(
        "url" -> "jdbc:mysql://localhost:3306/scott?characterEncoding=utf-8",
        "driver" -> "com.mysql.jdbc.Driver",
        "dbtable" -> "emp",
        "user" -> "root",
        "password" -> "123"
      )).load()
    spark.read.format("jdbc").jdbc(url,"emp",conn)

    // 读取数据
    val jdbc: DataFrame = spark.read.jdbc(url, "emp", conn)

    jdbc.printSchema()

    val result1: Dataset[Row] = jdbc.where("sal > 2500").select("empno")

    // 写数据
    result1.write.mode(SaveMode.Append).jdbc(url, "emp10", conn)
    spark.close()
  }
}
