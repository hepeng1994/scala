package xiaoniu34.day03

/**
  * Created by Huge
  * Desc:
  * apply方法
  */

class ApplyDemo private {

}
object ApplyDemo {
  private val ad:ApplyDemo = new ApplyDemo()

  // 类似于java中的方法重载
  /*def apply() = {
    println("hello apply")
  }*/
  def apply():ApplyDemo={
//    new ApplyDemo()
    ad
  }

  def apply(x: Int) = {
    println(s"x=$x")
    x
  }

  def apply(x: String, y: String) = {
    println(s"x=$x,y=$y")
  }

  def main(args: Array[String]): Unit = {

    // 显然不能再对象后面加上（）
    println(ApplyDemo)
    //    println(ApplyDemo.apply())
    // 这种写法是调用 apply方法的简写
    println(ApplyDemo())

    println(ApplyDemo.apply(10))

    println(ApplyDemo(10)) // apply  方法名称固定

    // 对象名(参数列表) ---》  调用 对象名.apply(参数列表)   参数个数，参数的类型

    ApplyDemo("str", "st") //

    // 简单   apply 封装好了一些通过的代码，方便的进行初始化赋值
    val arr = Array(1,3,5,6,8,0)

   /* val arr2 = new Array[Int](6)
    arr2(0) = 1
     arr(1) = 3*/

    println(ApplyDemo) // object
    println(ApplyDemo) // object
    println(ApplyDemo()) // class
    println(ApplyDemo()) // class

    // 如何保证实例是唯一的  单例

    // 1 对象本身就是单例的
    // 2 private  在伴生对象中还是可以new 类的实例的，其他类中不能new 实例
    // 3 ,在伴生对象中，只创建一个实例，然后通过apply方法，来返回这个实例

  }
}
