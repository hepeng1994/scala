package day06.zhang.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zhang on 7/28.
  * dsl sql
  */
object SparkSqlTest extends App{
  //配置信息
  val conf = new SparkConf().setAppName("SparkSqlHello").setMaster("local[*]")
  //创建sparksession
  val spark = SparkSession.builder().config(conf).getOrCreate()

  val employee: DataFrame = spark.read.json("C:\\it1804班级\\bigdate34\\day05\\资料\\employees.json")
 // employee.printSchema() //打印表头信息
   //employee.select("name").show()
  // employee.select(employee.col("name"),employee.col("salary").plus(10000)).where(employee.col("salary").plus(10000).gt(13500)).show();

  employee.select(employee.col("name"),employee.col("salary").plus(10000)).filter(employee.col("salary").plus(10000).gt(13500)).show();

  spark.stop()

}
