package xiaoniu34.day04

/**
  * Created by Huge
  * Desc:
  * 主构造器 直接和类的定义交织在一起
  * 辅助构造器
  */

// 定义了两个参数的主构造器  只能有一个
class Person (val name:String, var age:Int){

  println("constructor xxxxx")
  // 成员属性
  var fv:Double = _
  // 写或者不写的区别：
  // 没有写，就是在类的范围内有效，仅仅是一个局部变量
  // 使用val  或者 var  成员变量  可以在其他的地方进行访问

  // 也可以定义无参的构造方法   辅助构造器 可以定义多个  辅助构造器的方法名称 this
  // 定义无参的辅助构造器
  def this(){
    // 所有的辅助构造器，必须在第一行 调用主构造器或者其他的辅助构造器
    this("reba",23) // 必须写在第一行
    println("no params")
    //    this(name,age) // 必须写在第一行  主构造器的参数，必须要初始化赋值
  }

  // 辅助构造器的参数和主构造器的参数不能完全一致（参数类型，参数个数，参数顺序）
/*  def this(age:Int,name:String){
    this("xx00",12)
  }*/

  // baq  48 30
  def this(name1:String,age1:Int,fv:Double){
    // 赋值
    this(name1,age1)
    //    this(name1,age1) // 这个是调用主构造器的
    this.fv = fv
    println("three params")
  }

  // 构造器的作用域
  // 辅助构造器的作用域：  在自己的方法范围之内
  // 主构造器的范围是类的范围中除了 方法和成员属性之外的所有区域

  println("constructor ooooo ")
}

object Person{
  def main(args: Array[String]): Unit = {
    // java 无参的构造方法，一旦写了有参的构造方法之后，无参构造被覆盖
//    val p:Person = new Person("name",20)
//    println(s"${p.name}，age=${p.age}")

//    val p2 = new Person()
//    println(s"${p2.name}，age=${p2.age}")

    val p3 = new Person("baby",30,90)
    println(s"${p3.name}，age=${p3.age},fv=${p3.fv}")


  }
}

