package xiaoniu34.day03

/**
  * Created by Huge
  * Desc: 
  */

class Person {

  // 下下滑线表示占位符
  var name:String = _   // 初始值是null
  val account:String ="shl@qq.com"   // java

  var age:Int = 45
}

object Person{

  val name2 = "zl"
  val age2 = 30
  def main(args: Array[String]): Unit = {
    val p = new Person()

    println(p.name)
  }
}