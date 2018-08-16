package xiaoniu34.day01

object test {
  def main(args: Array[String]): Unit = {
    val array = Array(1, 2, 3, 4, 5, 6)
    val result: Array[Int] = for (a <- array) yield a * 10
    for (a <- result) println(a)
  }
}
