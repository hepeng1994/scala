package sparksql.datasource

import org.apache.spark.sql.{Dataset, Row, SparkSession}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc: 
  */

object ParquetSource2 {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    // 读取 parquet文件的数据
    val file: Dataset[Row] = spark.read.parquet("F:\\company\\edu\\course\\spark\\spark-08/2016-03-04_19_p1.1457094602020.log.20160304204026927.parquet")

    file.printSchema()

    //
    println("-----counts -= "+file.count())

//    file.rdd.coalesce(1).saveAsTextFile("output3")


    file.show(1)

    spark.close()
  }
}
