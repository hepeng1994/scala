package xiaoniu34.day04

/**
  * Created by Huge
  * Desc: 
  */

abstract class Nvshen(fv:Double) {

  val age = 18

  def getAge()={
    age
  }
  // 抽象的方法
  def eat()
}

// interface implements
// 实现 混入特质  extends with
trait TraitDeom1

object Test{
  def main(args: Array[String]): Unit = {
    val nvshen = new Nvshen(20) {
      override def eat(): Unit = {
        println("xxoo ")
      }
    }
    nvshen.eat()
  }
}