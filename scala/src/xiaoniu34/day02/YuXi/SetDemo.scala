package xiaoniu34.day02.YuXi

object SetDemo {
    def main(args: Array[String]): Unit = {
        val s = Set[Int](13, 4, 5, 6, 6)
        import scala.collection.mutable._

        import scala.collection.mutable.Set
        import scala.collection.mutable.Set

        val ms = Set[Int](10)
        ms += (11, 14)
        ms ++= Set(11, 14)
        ms ++= Set(111, 114)
        ms --= Set(10)
        println(ms.head)
        println(ms.tail)
        ms.remove(2)

        ms.foreach(println)

    }
}
