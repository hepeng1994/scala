package xiaoniu34.day04.casedemo

/**
  * Created by Huge
  * Desc:  匹配数组
  */

object CaseDemoArray {

  def getMaxAndMin()={
    val arr= Array(1,3,5,7)
    (arr.max,arr.min)
  }

  def main(args: Array[String]): Unit = {

    val (key,value) = getMaxAndMin()


    // 用模式匹配的方式直接接收参数
    val Array(a, b, c) = args  // String

    println(s"a=${a},b=${b},c=${c}")





    val a1: Array[Int] = Array(1, 3, 5)
    a1 match {
      //      case  Array(a,b,c) => println(s"a=$a,b=${b},c=${c}")
      case Array(2, a, b) => println(s"xxxxxa=$a,b=${b},c=${0}")
      // 可变参数列表
      //      case Array(1,_*) => println("match")
      case Array(1, _, _) => println("match2") // 格式化代码的快捷键  ctrl + alt + l
    }

  }
}
