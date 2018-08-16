package day01

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Huge
  * DATE: 2018/6/14
  * Desc:
  * spark scala 语法的wordcount
  */

object ScalaWordCount {
    def main(args: Array[String]): Unit = {
        if(args.length!=2){
            println("Usage:day01.ScalaWordCount <input> <output>")
            sys.exit(1)
        }
        //参数接收
        val Array(input,output)=args
        val conf = new SparkConf()
        //创建sparkcontext
        val sc = new SparkContext(conf)
        //读取数据
        val file = sc.textFile(input)
        //切分压平
        val words = file.flatMap(_.split(" "))
        //组装
        val wordAndOne = words.map((_,1))
        //分组聚合
        val result = wordAndOne.reduceByKey(_ +_)
        //排序 降序
        val finalRes = result.sortBy(_._2,false)
        //直接储存到hdfs
        finalRes.saveAsTextFile(output)
        //释放资源
        sc.stop()
    }
}
