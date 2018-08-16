package xiaoniu34.day01

object ForDemo {
  def main(args: Array[String]): Unit = {
    val array = Array(1, 2, 3, 4, 5, 6)
    for (num <- array) println(num)
    println("---------------------------")
    for (num <- array) {
      println(num)
    }
    println("---------to是闭区间----------------")
    //to是闭区间[  ]
    for (num <- 0 to array.length - 1) {
      println(array(num))
    }
    println("----------until为左闭右开区间 ---------------")
    //until为左闭右开区间    结果不一样
    for (num <- 0 until array.length - 1) {
      println(array(num))
    }
    println("------------for循环可以增加守卫----------------")
    //for循环可以增加守卫
    for (num <- array if (num % 2 == 0)) println(num)
    println("-------------双重循环方式一--------------")
    // 双重循环方式一
    for (i <- array) {
      for (j <- array) {
        println(i * 10 + j)
      }
    }
    println("-----------双循环方式二------------------")
    //双循环方式二
    for (i <- array; j <- array) println(i * 10 + j)
    println("----------双循环带守卫一------------")
    for (i <- array) {
      for (j <- array) {
        if (j != i)
          println(i * 10 + j)
      }
    }
    println("--------------双循环方式带守卫二---------")
    for (i <- array; j <- array if j != i) println(i * 10 + j)

    println("----------------99乘法表--------------------")
    for (i<- 1 to  9) {

      for ( j<- 1 to i) {
        print(i+"*"+j+"=" +i*j+"\t")
      }
      println()
    }

println("-------------------------yield--------------")

    val result: Array[Int] = for (a <- array) yield a * 10
    for (a <- result) println(a)
  }
}
