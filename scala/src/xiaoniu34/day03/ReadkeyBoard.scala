package xiaoniu34.day03

import scala.io.StdIn

/**
  * Created by Huge
  * Desc: 
  */

object ReadkeyBoard {

  def main(args: Array[String]): Unit = {

    //    val a:String = StdIn.readLine()

    // break  定义一个boolean类型的变量

    println("请输入一数字：")
    val i:Int = StdIn.readInt()
    println(i)

    // wordcount  hadoop scala  spark   _.length

    // 今日重点： 集合框架的方法综合练习   对象（单例，伴生对象，scala单例，apply方法）  类 的定义

    // 撸代码

    // 需求 key-value   value - key
    val lst = List("Id1-The Spark","Id2-The Hadoop","Id3-The Spark")
    // 1  数据拆分   需要拆两次
    lst.map(t=>{
      t.split("") // 得到一个数组 Array[String]
      // 获取key
      // 获取values
     val values :Array[String] =  "The Spark".split(" ") // 该数组中有两个元素
      //
      values.map(t=>{
        (t,"key")
      }) //  // Array[(String,String)]
      // 需要把数据压平

//      List[Array[(String,String)]]

     // List[(String,String)]

      // 按照新的key分组

      // 把key对应的所有的value 拼接成字符串  reduce  mkString("-")
    })
  }
}
