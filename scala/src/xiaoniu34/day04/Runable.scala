package xiaoniu34.day04

/**
  * Created by Huge
  * Desc: 
  */

trait Runable {

  // 定义一个抽象方法
  def run(msg:String)

  def fly(msg:String)={
    println(s"runnnn  fly with $msg")
  }
}
