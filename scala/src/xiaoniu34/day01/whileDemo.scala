package xiaoniu34.day01

object whileDemo {
  def main(args: Array[String]): Unit = {
//不能一起运行
    var a = 9
    /*方式一：break*/
    var flag = true/*标志位*/
    while(flag){
      println(a)
      a = a -1
      if(a == 5) flag=false
    }

    /*方式二*/
//    import scala.util.control.Breaks._
//    breakable {
//      while (true) {
//        println(a)
//        a = a - 1
//        if (a == 5) break
//      }
//    }
  }


}
