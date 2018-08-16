package xiaoniu34.day04.casedemo

/**
  * Created by Huge
  * Desc: 
  */

object MapGetValues {

  def main(args: Array[String]): Unit = {

    val mp = Map[String,Int]("a"->1,"b"->2,"c"->3)

    val maybeInt: Option[Int] = mp.get("a1")  // Some()  None

    // 使用模式匹配来获取值       模式匹配可以有返回值
    val result:(String,Int) = maybeInt match {
      case Some(d) => ("",1)
      case None => ("",12)
    }

    println(result)



  }
}
