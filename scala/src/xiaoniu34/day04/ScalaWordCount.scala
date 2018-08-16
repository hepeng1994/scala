package xiaoniu34.day04

/**
  * Created by Huge
  * Desc: 
  */

object ScalaWordCount {

  def main(args: Array[String]): Unit = {
    val arr =Array("hello spark","hello tom","hello hello")
    val words: Array[String] = arr.flatMap(_.split(" "))
    val map: Array[(String, Int)] = words.map(t=>(t,1))
    val grouped: Map[String, Array[(String, Int)]] = map.groupBy(_._1)
    val result: Map[String, Int] = grouped.mapValues(t => {
      val map1: Array[Int] = t.map(_._2)
      val sum: Int = map1.sum
      sum
    })

    result.toList.sortBy(-_._2).foreach(println)


  }
}
