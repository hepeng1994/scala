package sparksql.datasource

import org.apache.spark.sql._

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc:  读取json数据源
  */

object JsonSource2 {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    // dataset[Row]
    val json: DataFrame = spark.read.json("address.json")

    json.printSchema()

    json.createTempView("v_json")

    val result1 = spark.sql("select address.city,address.street from v_json")

//    result1.show()

    import org.apache.spark.sql.functions._
//    val explodeDF = json.select(explode(json("address"))).toDF("city","street").printSchema()

    spark.close()
  }
}
