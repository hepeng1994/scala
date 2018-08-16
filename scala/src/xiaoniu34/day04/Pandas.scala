package xiaoniu34.day04

/**
  * Created by Huge
  * Desc: 
  */

// 普通的类 直接实现特质
// 如果一个普通的类没有显示的继承一个父类（没有使用extends关键字）
// 那么混入（实现）的第一个特质，必须使用extends关键字
class Pandas extends Runnable with Flyable{

  // 如果重写一个抽象方法，可以不用写override关键字
   def run(): Unit = {
    println("卖萌就ok了")
  }

  // 重写一个普通方法 必须加上 override
  override def fly(msg: String): Unit = {

  }
}

object Pandas{
  def main(args: Array[String]): Unit = {
    val p = new Pandas()
    p.run()
//    println()
  }
}