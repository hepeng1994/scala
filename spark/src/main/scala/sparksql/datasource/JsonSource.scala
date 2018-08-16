package sparksql.datasource

import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql._

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc:  读取json数据源
  */

object JsonSource {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    // dataset[Row]
    val json: DataFrame = spark.read.json("product.json")

    json.printSchema()

    // json 格式的数据，生成的schema信息不是按照原有的数据顺序，是按照字典顺序得到的。
//    json.toDF("pname1","price1","amount1").show()


    val result: Dataset[Row] = json.filter("price > 1000")

    // 读寄送 写 json
    result.write.mode(SaveMode.Append).json("jsonout1")

    spark.close()
  }
}
