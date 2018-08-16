package streaming01

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordCount {
    def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Streaming").setMaster("local[*]")
     //创建context
        val ssc = new StreamingContext(conf,Seconds(5))
      //从socket接受数据
        val line = ssc.socketTextStream("192.168.212.100",8888)
        val word = line.flatMap(_.split(" "))
        val word2 = word.map((_,1))
        val word3 = word2.reduceByKey(_+_)
        word3.print()
        ssc.start()
        ssc.awaitTermination()

    }
}
