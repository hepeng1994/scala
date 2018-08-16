package sparksql

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, StructType}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc: 
  */

object UDAFDemo {

  def main(args: Array[String]): Unit = {
    // 几何平均数

    // 平方
    math.pow(3, 2)

    // 乘积的总和  1.0 / 次数
    math.pow(3, 1.0 / 2)

    // 2 3 4 5
    val result = math.pow(2 * 3 * 4 * 5, 1.0 / 4)
    println(result)

    // UDAF 的功能
    /** 思路：
      * 3个分区 2 3 4 5 6 7
      *
      *         2个中间值   乘积的累加值 1     次数 0
      * 分区0
      * 2 3                 1*2 = 2             0 + 1  = 1
      *                     2*3 = 6             1 + 1 = 2
      *
      * 分区1
      * 4 5
      *                   20              2
      * 分区2
      * 6   7
      *                   42              2
      * 合并的时候： 累加值 相乘  次数 相加
      *
      * math.pow(v1,1.0/v2)
      */
  }
}

class myMG extends UserDefinedAggregateFunction {
  override def inputSchema: StructType = ???

  override def bufferSchema: StructType = ???

  override def dataType: DataType = ???

  override def deterministic: Boolean = ???

  override def initialize(buffer: MutableAggregationBuffer): Unit = ???

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = ???

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = ???

  override def evaluate(buffer: Row): Any = ???
}
