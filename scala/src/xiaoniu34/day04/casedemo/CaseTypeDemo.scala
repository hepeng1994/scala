package xiaoniu34.day04.casedemo

import scala.util.Random

/**
  * Created by Huge
  * Desc:  匹配 类型
  */

object CaseTypeDemo {

  def main(args: Array[String]): Unit = {
    // 在数组中定义了4种数据类型
    val arr:Array[Any] = Array(1,23.5,true,"hello") // 父类

    val index = Random.nextInt(arr.length)
    println(s"index=${index}")

    arr(1) match {
        // 在匹配类型中，可以添加 守卫
      case x:Int => println(x*10)
      case y:Double if(y>50) => println(s"double=${y}")
      case b:Boolean => println(b)
      case z:String => println(s"str=${z}")

      case _ => println("no match")

    }

  }
}
