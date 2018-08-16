package xiaoniu34.day02.YuXi

object Demo1 {
      def sum (a:Int,b:Int)=a+b
      def smm (c:Int,b:Double)=c*b
      def method2(x:Int,y:Int):Double ={
        x*y*1.0
      }
      def add(x:Int,y:Int,f:(Int,Int)=>Double)=f(x,y)
  def main(args: Array[String]): Unit = {
    val sum1 = sum _
    println(sum(5,6))
    println(sum1(5,6))
    println(method2(6,5))
    //方法默认转换为函数
    println(add(5,6,method2))
    val arr =Array[Int](1,2,3,4)
    println("---------------------------")
    arr.foreach(println)
    println("--------------------------------")
    arr.foreach(println _)

  }
}
