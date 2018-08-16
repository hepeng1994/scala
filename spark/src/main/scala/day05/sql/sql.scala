package day05.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}

object sql extends App {
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    val spar: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    val frame: DataFrame = spar.read.json("E:\\x\\spark新\\spark5\\employees.json")
    //val sql: SQLContext = SQLContext(frame)
    //1.6版本可以用这个
    //val sqlContext: SQLContext = new SQLContext(sc)
   //sql.jsonFile("E:\\x\\spark新\\spark5\\employees.json")
    frame.show()
    frame.select("name").show()

    frame.createOrReplaceTempView("employee")

    spar.sql("select * from employee").show()

    frame.createGlobalTempView("person")
    spar.sql("select * from global_temp.person  where salary>4000").show()
    spar.udf.register("add",(x:String)=>"A:"+x)
    spar.sql("select add(name) from global_temp.person").show()
    spar.stop()

}
