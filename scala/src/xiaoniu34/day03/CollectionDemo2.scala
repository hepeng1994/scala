package xiaoniu34.day03

/**
  * Created by Huge
  * Desc: 
  */

object CollectionDemo2 {

  def main(args: Array[String]): Unit = {

//    println(new ApplyDemo())

    val arr =Array(1,2,4,3,5,6,8,9,10)
    // 把元素两两分组
    val grouped: Iterator[Array[Int]] = arr.grouped(2)
    while (grouped.hasNext){
      val next: Array[Int] = grouped.next()
      println(next.toList)
    }

    println(arr.count(_<0))
    println(arr.count(_ == 4))
  }

}
