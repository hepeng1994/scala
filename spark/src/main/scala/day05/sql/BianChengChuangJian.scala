package day05.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object BianChengChuangJian extends App {
    import org.apache.spark.sql.types._
    val sparkConf = new SparkConf().setAppName("score").setMaster("local[*]")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    // Create an RDD
    val peopleRDD = spark.sparkContext.textFile("E:\\x\\spark新\\spark5\\a.txt")

    // The schema is encoded in a string,应该是动态通过程序生成的
    val schemaString = "name age"

    // Generate the schema based on the string of schema   Array[StructFiled]
    val fields = schemaString.split(" ")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))

   //val filed = schemaString.split(" ").map(filename=> filename match{ case "name"=> StructField(filename,StringType,nullable = true); case "age"=>StructField(filename, IntegerType,nullable = true)} )

    val schema = StructType(fields)

    // Convert records of the RDD (people) to Rows
    import org.apache.spark.sql._
    val rowRDD = peopleRDD
      .map(_.split(","))
      .map(attributes => Row(attributes(0), attributes(1).trim)

      )

    // Apply the schema to the RDD
    val peopleDF = spark.createDataFrame(rowRDD, schema)

    // Creates a temporary view using the DataFrame
    peopleDF.createOrReplaceTempView("people")

    // SQL can be run over a temporary view created using DataFrames
   // val results =
        spark.sql("SELECT name FROM people").show()

    // The results of SQL queries are DataFrames and support all the normal RDD operations
    // The columns of a row in the result can be accessed by field index or by field name
    //results.map(attributes => "Name: " + attributes(0)).show()
    // +-------------+
    // |        value|
    // +-------------+
    // |Name: Michael|
    // |   Name: Andy|
    // | Name: Justin|
    // +-------------+

}
