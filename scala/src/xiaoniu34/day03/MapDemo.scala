package xiaoniu34.day03

import xiaoniu34.day04.Students

import scala.collection.immutable.HashMap
import scala.collection.mutable
/**
  * Created by Huge
  * Desc: 
  */

object MapDemo {

  def main(args: Array[String]): Unit = {

    // 依然不能运行
    val s = new Students()
    println(s.name)
    val mp = Map[String,Int]()
    val mp2 = new HashMap[String,Int]()


    // 两种导包方式
    val mp3 = mutable.Map[String,Int]()

    mp3 += ("a"->1)
    mp3 += (("b",12))
    mp3.put("c",13)
    mp3("d")= 121
/*

    for(i <- mp3){
      println(i._1,i._2)

    }

    for ((k,v) <- mp3){
      // 插值打印法
//      println("key=$k")
      println(s"key=${k},value=${v}")
    }


    // 单独打印key
    for( i<- mp3.keySet){
      println(i)
    }


    for(i<- mp3.values){
      println(i)
    }
*/


//    mp3("a1")
    // 可能有值 可能没有值
    // Option 两个子类
    // Some(value)    多例
    // None           单例
    println(mp3.get("c1"))
    println(mp3.get("c").get)

    // 更好的方法
    mp3.getOrElse("c1",-1)

  }
}
