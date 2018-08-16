package day06.zhang.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zhang on 7/28.
  * dsl sql
  */
object UDFTest extends App{
  //配置信息
  val conf = new SparkConf().setAppName("SparkSqlHello").setMaster("local[*]")
  //创建sparksession
  val spark = SparkSession.builder().config(conf).getOrCreate()

  //把数据进行加载
  val employee: DataFrame = spark.read.json("C:\\it1804班级\\bigdate34\\day05\\资料\\employees.json")
   //创建表
  employee.createTempView("person")
  spark.udf.register("add",(x:String)=>"A:"+x)

  spark.sql("select add(name),salary from person").show()

  spark.stop()

}
