package xiaoniu34.day03

/**
  * Created by Huge
  * Desc:
  * 利用一个App的特质（java中的接口）  来实现不写main方法，就可以打印内容
  */

object ApplicationDemo extends App{

  // 方法的重写
  override def main(args: Array[String]): Unit = {
    println(1232435)
  }

}
