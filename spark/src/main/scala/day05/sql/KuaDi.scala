package day05.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Dataset, SQLContext, SparkSession}
case class KuaDi(kuaid:String,startData:String,endData:String)
object KuaDi extends App {
    val conf: SparkConf = new SparkConf().setAppName("sql").setMaster("local[*]")
    val spar: SparkSession = SparkSession.builder().config(conf).getOrCreate()


   import spar.implicits._
   val kuaidi: RDD[String] = spar.sparkContext.textFile("E:\\x\\spark新\\spark5\\day052\\快递.txt")
     val kuaudi: Dataset[KuaDi] = kuaidi.map(x => {
        val strings = x.split(" ")
        val kuaid = strings(0)
        val startData = strings(1)
        val endData = strings(2)
        KuaDi(kuaid, startData, endData)
    }).toDS()
    kuaudi.createOrReplaceTempView("kuaidi")
   //cast(avg(t.shijian) as decimal(12,2))保留两位小数     datediff(endData,startData)  日期计算
    spar.sql("select t.kuaid,cast(avg(t.shijian) as decimal(12,2))as shijian2 from (select kuaid,datediff(endData,startData) as shijian from kuaidi) as t group by t.kuaid").show()
    //cast(avg() as decimal(12,2))
    spar.stop()
}
