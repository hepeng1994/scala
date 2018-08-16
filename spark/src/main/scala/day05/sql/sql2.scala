package day05.sql


import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.expressions.MutableAggregationBuffer
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
//sql实现udaf
object MyAverage extends UserDefinedAggregateFunction {
    // 聚合函数输入参数的数据类型
    def inputSchema: StructType = StructType(StructField("inputColumn", LongType) :: Nil)
    // 聚合缓冲区中值得数据类型
    def bufferSchema: StructType = {
        StructType(StructField("sum", LongType) :: StructField("count", LongType) :: Nil)
    }
    // 返回值的数据类型
    def dataType: DataType = DoubleType
    // 对于相同的输入是否一直返回相同的输出。
    def deterministic: Boolean = true
    // 初始化
    def initialize(buffer: MutableAggregationBuffer): Unit = {
        // 存工资的总额
        buffer(0) = 0L
        // 存工资的个数
        buffer(1) = 0L
    }
    // 相同Execute间的数据合并。
    def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
        if (!input.isNullAt(0)) {
            buffer(0) = buffer.getLong(0) + input.getLong(0)
            buffer(1) = buffer.getLong(1) + 1
        }
    }
    // 不同Execute间的数据合并
    def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
        buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
        buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
    }
    // 计算最终结果
    def evaluate(buffer: Row): Double = buffer.getLong(0).toDouble / buffer.getLong(1)
}



object sql2 extends App {
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
    spar.udf.register("myAverage", MyAverage)
    spar.sql("select myAverage(salary) from global_temp.person ").show()
    spar.stop()
}
