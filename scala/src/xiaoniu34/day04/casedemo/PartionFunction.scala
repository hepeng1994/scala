package xiaoniu34.day04.casedemo

/**
  * Created by Huge
  * Desc: 
  */

object PartionFunction {

  // 只有一个输入参数，一个返回值类型  这种模式匹配，可以用偏函数来简写
  def mat(x:Int) = {
    x match {
      case 10 => println("hehe")
      case 20 => println("haha")
      case _ => println("xxx")
    }
  }

  def mat12(x:Any) = {
    x match {
      case 10 => 10
      case 20 => 20
      case _ => 20
    }
  }

  // 只有case语句  没有match关键字   偏函数  这是偏函数附带的功能
  def mat2:PartialFunction[Int,Unit] ={
    case 10 => println("hehe")
    case 20 => println("haha")
    case _ => println("xxx")
  }

  def mat3:PartialFunction[Any,Int] ={
    case x:Int => x*10
  }

  // 偏函数  可以对某一类或者某一些类别的数据进行处理

  def main(args: Array[String]): Unit = {
    mat(320)

    mat2(320)


    val arr = Array(1,3,5,"error")
    // filter
//    arr.map(_*10)

    // 有这么一类方法，接收的参数，就是一个偏函数 ，那么在这类方法中，可以对特定的数据进行处理
    // pf: PartialFunction[A, B]   // 输入参数类型是Any
    val collect: Array[Int] = arr.collect(mat3)
    println(collect.toBuffer)




  }
}
