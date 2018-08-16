package xiaoniu34.day01

object Function {
  val a, c = 10
  val b = 11
  //  res26: (Int, Int) => Int = <function2>
  /*函数的定义有两种*/
  (a: Int, b: Int) => a + b /*匿名函数*/
  /*方式一*/

  /*val   函数名称  ：  （参数名称：参数的类型...) =>  业务运算逻辑*/
  val f1 = (a: Int, b: Int) => a + b
  val f5= (a: Int, b: Int, c: Int) => a + b + c /*此函数是方式二的f3函数的另一种写法*/

  /*方式二*/
  val f0: Int => Int = a => a + 1
  val f2: (Int, Int) => Int = (a, b) => a + b
  val f4: (Int, Int) => Double = (a, b) => a + b * 1.0
  val f3: (Int, Int, Int) => Int = (a, b, c) => a + b + c

  /**
    * 定义一个方法，这个方法中接受两个参数
    *
    * @param a  ： 一个具体的值
    * @param f1 ：传入的函数---这个函数只接受1个参数, 名字是自定义的
    * @return
    */
  def callFuc_1(a: Int, f1: Int => Int) = {
    f1(a)
  }

  def callFuc_2(a: Int, b: Int, sdfsdfsdf: (Int, Int) => Int) = {
    sdfsdfsdf(a, b)
  }

  def callFuc_3(a: Int, b: Int, sdfsdfsdf: (Int, Int) =>Double) = {
    sdfsdfsdf(a, b)
  }

  def main(args: Array[String]): Unit = {

    //    println(f0(1))
    //    println(f1(a, b))
    //    println(f2(a, b))
    //    println(f3(a, b, c))
    //    println(callFuc_1(a, f0))
    //    println(callFuc_2(a,a, f2))  /*此处的f2并不是随便瞎取得，是我们在前面预先定义的函数名称*/
    //    println(callFuc_3(a,a, f4))  /*此处的f2并不是随便瞎取得，是我们在前面预先定义的函数名称*/
    println(callFuc_3(a, a, (x: Int, y: Int) => x * y)) /*传入匿名函数*/

    //    res27: (Int, Int, (Int, Int) => Double) => Double = <function3>

    println(callFuc_3 _ )

  }
}
