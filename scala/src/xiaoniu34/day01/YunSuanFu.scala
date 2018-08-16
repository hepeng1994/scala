package xiaoniu34.day01

object YunSuanFu {
  def main(args: Array[String]): Unit = {
    //Scala 中的+-*/%等操作符的作用与 Java 一样，位操作符 &|^>><<也一样。只是有
    //一点特别的：这些操作符实际上是方法。例如：
    //a+b 是如下方法调用的简写：
    //a.+(b) a 方法 b 可以写成 a.方法(b)
    val a = 2;
    val b = 3;
    println(a + b)
    println(a.+(b))
   // for (i <- 1 to 9 ; j <- 1 to i ) println(i+"*"+j+"=" +i*j+"\t")
    for (i<- 1 to  9) {

      for ( j<- 1 to i) {
        print(i+"*"+j+"=" +i*j+"\t")
    }
      println()
    }

  }
}
