package sparksql.datasource

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc: 
  */

object FileSource {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import spark.implicits._

    // 读取文件
    val file: Dataset[String] = spark.read.textFile("person.txt")


    //    val text: DataFrame = spark.read.text("person.txt")

    val df: DataFrame = file.map(t => {
      val split = t.split(" ")
      (split(0), split(1).toInt, split(2))
    }).toDF("name", "age", "fv")

    // 写文件  text 写的api 只支持一列
    df.select("name").write.text("output1")

    // saveApi 写的文件格式是Parquet文件 独有的读写API
    df.write.save("output2")

    spark.close()
  }
}
