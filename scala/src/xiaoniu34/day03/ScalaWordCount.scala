package xiaoniu34.day03

/**
  * Created by Huge
  * Desc: 
  */

object ScalaWordCount {

  def main(args: Array[String]): Unit = {
    val arr =Array("hello spark","hello tom","hello hello")
    // 分割并压平  flatMap  map  +  flatten
    val data = arr.flatMap(_.split(" "))

    // 单词和1 组装
    val wordWithOne:Array[(String,Int)] = data.map((_,1))

    // 根据key来进行分组  map的key 是由 分组的条件
    val grouped: Map[String, Array[(String, Int)]] = wordWithOne.groupBy(_._1)
//    val by1: Map[Int, Array[(String, Int)]] = wordWithOne.groupBy(_._2)

    // 禁止使用
//    grouped.mapValues(_.length)

    // Array[(String, Int)]  --> Array[Int]  第二个元素的和   sum

    // 在scala中，mapValues 只能作用于map类型的集合  spark   k-v类型的结合
    val result: Map[String, Int] = grouped.mapValues(t => {
      val intArr: Array[Int] = t.map(tp => {
        tp._2
      })
      intArr.sum
    })
    // foreach
//    result.foreach(println)

    //
    result.toArray.sortBy(- _._2).foreach(println)

  }
}
