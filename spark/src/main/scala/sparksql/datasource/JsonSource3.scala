package sparksql.datasource

import org.apache.spark.sql._

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc:  读取json数据源
  */

object JsonSource3 {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()
    import spark.implicits._

    // dataset[Row]
    val json: DataFrame = spark.read.json("address2.json")

    json.printSchema()


    //    result1.show()

    import org.apache.spark.sql.functions._
    json.select($"name", explode($"address")).toDF("name", "city")
    .show()


    spark.close()
  }
}
