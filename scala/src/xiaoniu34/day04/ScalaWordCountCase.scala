package xiaoniu34.day04

import scala.collection.immutable

/**
  * Created by Huge
  * Desc:
  * 在 代码中使用偏函数
  */

object ScalaWordCountCase {

  def main(args: Array[String]): Unit = {
    val arr =Array("hello spark","hello tom","hello hello")
    // 分割并压平  flatMap  map  +  flatten
    val data = arr.flatMap(_.split(" "))

    // 单词和1 组装
    val wordWithOne:Array[(String,Int)] = data.map((_,1))

    // 根据key来进行分组  map的key 是由 分组的条件
    val grouped: Map[String, Array[(String, Int)]] = wordWithOne.groupBy(_._1)
//    val by1: Map[Int, Array[(String, Int)]] = wordWithOne.groupBy(_._2)


//    grouped.map(t=>)

    val map: Map[String,Int] = grouped.map({ // 类型是一个元素  String, Array[(String, Int)   k  -  v
      // 特殊的偏函数
      case (k, arr) =>
        // 要对arr   Array[(String, Int)]
        val ints: Array[Int] = arr.map({
          // 元素类型 又是  元组
          case (_, cnts) => cnts
        })
        (k,ints.sum)
    })
    map.foreach(println) //1 大括号   2，元组有几个元素，就要用几个变量去接收
  }
}
