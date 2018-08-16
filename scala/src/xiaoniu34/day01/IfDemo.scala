package xiaoniu34.day01

object IfDemo {
  def main(args: Array[String]): Unit = {
    val a :Int = 9
    if(a>6) println("nihao")
    println("------------------------------------")
    if (a>6){
      println("wojiao")
    }
    println("------------------------------------")

    if (a<58){
      println("hepeng")
    }else{
      println("zhengguiqiang")
    }
    println("------------------------------------")
    //if能够赋值给变量,赋值为if语句的执行的最后一行,如果为print输出语句返回nuit
    val result=if(a>96){
      println("woshixiaohou")
    }else{
      println("nishishui")
      1233
    }
    println(result)
    println("------------------------")
    val res=if(a>46) a else "nahsisha"
    println(res)

    println("---------------------------")
    val res1=if (a>6&&a<8)"及格"
    else if(a>8) "优秀"

    println(res1)


  }
  
}
