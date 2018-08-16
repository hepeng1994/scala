package xiaoniu34.day03

/**
  * Created by Huge
  * Desc: 
  */

class Demo {
  private val name2:String = "haoran"

  // class 可以 有main方法，但是就是一个普通方法

}
// 条件：  1，必须在同一个源文件中  2 ，类名和对象名必须相同
// 共享   伴生类和伴生对象之间，可以访问对方的私有属性和私有方法
object Demo{
  // 成员属性
  val name:String = "baoqiang"  // get set

  val age:Int = 48

  // scala 中不需要有
  // 成员方法
  def getAge():Int = this.age


  def main(args: Array[String]): Unit = {
    println(2222)

    // Demo2 是class
    println(new Demo())
    println(new Demo())
    println(new Demo())

    // 对象 单例 静态的
    println(Demo)
    println(Demo)
    println(Demo)

    println(Demo.age)
    println(Demo.getAge())
    val demo = new Demo()
    demo.name2


  }




}
