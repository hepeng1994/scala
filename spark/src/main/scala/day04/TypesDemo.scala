package day04

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/21
  * Desc: 
  */

object TypesDemo {

  def main(args: Array[String]): Unit = {
    val c:Any = 100

    // 类型强转
    val d = c.asInstanceOf[Int] + 3
    println(d)


    val str:String = "nvshen"

    println(str.isInstanceOf[String])

  }
}
