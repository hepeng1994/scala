package day06.zhang.sparksql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}

/**
  * Created by zhang on 7/28.
  * dsl sql
  */
object SparkSqlHello extends App{
  //配置信息
  val conf = new SparkConf().setAppName("SparkSqlHello").setMaster("local[*]")
  //创建sparksession
  val spark = SparkSession.builder().config(conf).getOrCreate()
  //var sc =  spark.sparkContext
  //var sc = new SparkContext(conf)
  //var scontext = new SQLContext(sc)
  val employee: DataFrame = spark.read.json("C:\\it1804班级\\bigdate34\\day05\\资料\\employees.json")
 // employee.printSchema() //打印表头信息
 // employee.select("name").show()
  //employee.createGlobalTempView("person")
  employee.createTempView("person")

  //spark.sql("select * from global_temp.person where  salary > 3000").show()

 //print(spark.sql("select * from Person where salary > 3000").rdd.collect())
  //spark.udf.register("add",(x:String)=>"A:"+x)
  //spark.sql("select add(name) from person").show()

  spark.stop()

}
