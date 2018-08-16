package xiaoniu34.day01

object Function1 {
  val a= 5
  val b,c =10
  val f1:(Int,Int)=>Int=(a,b)=>a+b
  val f2=(a:Int,b:Int)=>a+b

  def sun (a:Int,b:Int,f:(Int,Int)=>Int)={
    f(a,b)
  }

  def main(args: Array[String]): Unit = {
    println(f1(a,b))
    println(f2(a,b))
    println(sun(a,c,f2))
    println(sun(a,b,(x:Int,y:Int)=>x*y))
//将方法转换成函数
    println(sun _)

  }
}
