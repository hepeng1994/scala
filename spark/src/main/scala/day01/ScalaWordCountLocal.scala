package day01
import org.apache.spark.{SparkConf, SparkContext}

object ScalaWordCountLocal {
    def main(args: Array[String]): Unit = {
//        if (args.length!=2){
//            println("Usage :day01.ScalaWordCountLocal <input> <output>")
//            sys.exit(1)
//        }
        //参数接收
        val Array(input,output)=args
        val conf = new SparkConf()
        conf.setMaster("local[3]")
        conf.setAppName(this.getClass.getSimpleName)
        //创建sparkcontext
        val sc = new SparkContext(conf)
        //读取数据
        val file = sc.textFile(input)
        //切分并压平
        val words = file.flatMap(_.split(" "))
        //组装
        val wordAndOne = words.map((_,1))
        //分组聚合
        val result = wordAndOne.reduceByKey(_ + _)
        //排序 降序
        val finalRes = result.sortBy(_._2,false)
        //获取分区数据的API
        println(finalRes.partitions.size)
        //直接储存到hdfs中
        finalRes.saveAsTextFile(output)
        //释放资源
        sc.stop()
    }
}
