package xiaoniu34.day04.casedemo

/**
  * Created by Huge
  * Desc: 
  */

object CaseDemo2 {

  def main(args: Array[String]): Unit = {
    // 匹配元组
   /* val tp = (1,22.2,true,"str")
    tp match {
//      case (5,b,x:Boolean,d) => println(b)
        // 使用元组进行匹配，需要保证 数据类型 和元素的个数 必须完全一致
      case (a,23d,_,_) => println(s"a+${a},b=${12}")
      case _ => println("no match")
    }*/

    // List集合
    val  lst = List(9,5,2,7)
    // List 集合 头元素  尾列表

    lst match {
      case List(1,b,c,d) => println(s"a+${1},b=${b},c=${c},d=${d}")
      case a::b::c::Nil => println(s"a=${a},b=${b},c=${c}")
      case a::b::c  => println(s"aaaaaa=${a},b=${b},c=${c}")
      case _ => println("mo match ")
    }





    // List集合
  }

}
