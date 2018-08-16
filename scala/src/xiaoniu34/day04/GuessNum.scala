package xiaoniu34.day04

import scala.io.StdIn
import scala.util.Random

/**
  * Created by Huge
  * Desc: 
  */

object GuessNum {
  def main(args: Array[String]): Unit = {

    // StdIn.readInt()  读取键盘的一个int输入
    // 获取一个随机数组
    val num = Random.nextInt(100)

    println(s"num=${num}")
    // 满足条件 执行退出
    var flag = true
    while (flag){
      println("请输入一个数字：")
      val inNum = StdIn.readInt()
      // 执行判断
      if(inNum < num){
        println("太小了，请再次输入：")
      }else if(inNum > num){
        println("太大了，请再次输入：")
      }else{
        println("恭喜你，猜对了")
        flag = false
      }
    }
  }
}
