package sparksql.datasource

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc: 
  */

object ParquetSource {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import spark.implicits._

    // 读取 parquet文件的数据
    val file: Dataset[Row] = spark.read.parquet("output2")

    file.printSchema()

    file.show()

    val result: Dataset[Row] = file.filter("age > 30")

    // 写到parquet
    result.write.parquet("parquetout1")

    spark.close()
  }
}
