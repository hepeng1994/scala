package xiaoniu34.day01

object FangFa {
//def  方法的名称(参数列表):返回值类型 = {
  //  方法体内容
  //方法的返回值类型可以不写，编译器可以自动推断出来， 但是对于：
  // 返回值使用了 return 关键字的； 递归函数，必须指定返回类型；
  val a =10 ;
  val b=12 ;
  //把同一个值赋给不同的参数
  //val a,b,c=10;
  //定义一个无参方法 ,方法有(),在调用时()可有可无  方法没有(),调用时也不能有
  def sum_1 (): Int ={
    a+b
  }
  //无参方法二
  def sum_2 : Int ={
    a+b
  }
  //无参方法三
  def sum_3():Int=a+b
  def sum_4:Int=a+b
  //定义一个有参方法
  def sum_5(c:Int):Int=a+b+c
  def sum_6(c:Int, d:Double)={
    (a+b+c)*d
  }
  //def sum_6(c:Int, d:Double)=(a+b+c)*d
  /*带默认值的方法*/
  def sum_7(c: Int, d: Double = 2.0) = {
    (a + b + c) * d
  }
  /*多个参数的方法*/
  def sum_8(numbers: Any*) = {
    for (n <- numbers) {
      println(n)
    }
    println(numbers.size)//此处作为返回值所以为空nuit,  因为输出就是nuit
  }
  /*有两种情况必须指定方法的返回值类型*/
  /*1. 如果方法使用了return的操作*/
  def sum_9(c: Int = 1, d: Double = 2.0): Double = {
    return (a + b + c) * d
  }

  /*2. 如果方法是一个递归的方法*/
  /*演示阶乘：5！= 5 * 4 * 3 * 2 * 1*/
  def rec(n: Int): Int = {
    if (n == 1) 1
    else n * rec(n - 1)
  }
  def main(args: Array[String]): Unit = {
    println(sum_1())
    println(sum_2)
    println(sum_3())
    println(sum_4)
    println(sum_5(6))
    println(sum_6(4,5.5))
    println(sum_7(6))
    println("--------------------")
   println(sum_8(9,8,5,6,12,3,5))
  println(sum_9())
    println(rec(6))
  }
}
