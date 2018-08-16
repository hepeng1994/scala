package xiaoniu34.day04

/**
  * Created by Huge
  * Desc: 
  */

class Pig extends Animal with Flyable with Runable{
  // ctrl + i 实现方法   ctrl+ o 重写方法   alt + Enter 导包
  // 在子类中实现了一个抽象方法
  override def speak(): Unit = {
    println("惨烈的哼哼")
  }

  override def run(msg: String): Unit = {
    println("run with ")
  }

  // 在定义一个方法
  override def fly(msg:String)={
    super[Runable].fly(msg)
  }

//  override def fly(msg: String): Unit = super.fly(msg)
}

object Pig{

  // 程序的执行入口
  def main(args: Array[String]): Unit = {

    val p1:Pig = new Pig()
    p1.fly("f1")
/*
    p1.eat("123")

    // 多态
    val p2:Animal = new Pig()
    /**
      * 多态的条件：
      * 1，继承或者实现接口
      * 2，子类重写父类方法，实现类实现接口方法
      * 3， 父类引用指向子类实例，接口引用指向实现类
      */
    // scala java 三大特性  封装  继承  多态

    println(p1.speak())
    println(p1.name)
    println(p1.eat("士力架"))

    println(p2.speak())

    println(p1.fly("台风 来了，二师兄也能飞起来"))*/

    // 时代
    // 风口浪尖

  }
}