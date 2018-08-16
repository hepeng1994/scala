package sparksql.datasource

import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc:  csv数据源
  */

object CsvSource {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import spark.implicits._

    // 读取文件
    val file: Dataset[Row] = spark.read.csv("product.csv")

    file.printSchema()
    /**
      * root
      * |-- _c0: string (nullable = true)
      * |-- _c1: string (nullable = true)
      * |-- _c2: string (nullable = true)
      */


    val config = ConfigFactory.load()

    // 读取mysql数据库  ---》  操作 之后 ---》 写到mysql中

    val url = config.getString("db.url")
    val conn = new Properties()
    conn.setProperty("user", config.getString("db.user"))
    conn.setProperty("password", config.getString("db.passwd"))

    // 读取数据
    val jdbc: DataFrame = spark.read.jdbc(url, "emp", conn)

    jdbc.filter("sal >2600").write.csv("csvpath1")

    spark.close()
  }
}
