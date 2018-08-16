package xiaoniu34.day04

/**
  * Created by Huge
  * Desc:
  * 定义一个抽象类，动物
  */

abstract class Animal {

  var name:String =_
  // 定义普通方法
  def eat(food: String): Unit = {
    println(s"eat $food")
  }

  // 定义抽象方法
  def speak()
}
