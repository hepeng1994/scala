package xiaoniu34.day04.casedemo

import scala.util.Random

/**
  * Created by Huge
  * Desc:  匹配样例类  和样例对象
  */

object CaseClassDemo {

  def main(args: Array[String]): Unit = { 

    // 创建一个数组，用于封装 case class  case object
    val arr = Array(Register("w1",10),Register("w2",20),HeartBeat(1800),CheckStatus)

    // 随机取
    val index = Random.nextInt(arr.length)
    println("index="+index)
    println("values="+arr(index))

    arr(3) match {
        // 样例类怎么写匹配么？
      case Register(wId,c)=>{
        println(s"wId=${wId},c=${c}")
      }
      case HeartBeat(t) => println(t)
      case CheckStatus => println("case object")
      case _ => println("no match ")
    }



  }
}

// 样例类和样例对象 ，首字母，必须大写
// 定义一个样例类
case class Register(workerId:String,cores:Int)

case class HeartBeat(time:Long)

// 样例对象
case object CheckStatus

