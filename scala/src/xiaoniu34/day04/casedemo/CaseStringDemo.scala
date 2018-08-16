package xiaoniu34.day04.casedemo

// 包可以在任意需要的地方导入
import scala.util.Random

/**
  * Created by Huge
  * Desc: 
  */

object CaseStringDemo {

  def main(args: Array[String]): Unit = {
    // 给定数据
    val arr = Array("YoshizawaAkiho", "YuiHatano", "AoiSola")

    // 拿一条数据进行匹配
    //
    val index = Random.nextInt(arr.length) // 3      0  1  2  [  )
    println(s"index = $index")

    // 模式匹配是有返回值的
    arr(index) match {
      case "YuiHatano" => println("xx老师")
      case "AoiSola" => println("oo老师")
        // 如果前面都没有匹配上
      case _ => println("no match")
    }

  }
}
