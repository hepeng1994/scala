package xiaoniu34.day03

import xiaoniu34.day04.Students

/**
  * Created by Huge
  * Desc:
  * 计算一个Array[Double]数组的平均值   总和   /  长度
  *
  *
  * 2，编写一个函数或方法，返回数组中最小值和最大值的对偶  （元组）
  * 3，编写一个函数或方法，getValues(values: Array[lnt]，v：Int)，返回数组中小于v、等于v和大于v的元素个数，要求三个值一起返回
  * 4，数组反转，两两交换 Array(1,2,3,5,6,7,9)  2 1 5 3 7 6 9  to  until 步长
  *
  */

object Test1 {

  // 定义一个方法  求 数组的平均值
  def avg(arr: Array[Int]): Double = {
    // avg = sum  / length
    val sum = arr.sum
    val len = arr.length
    sum * 1.0 / len
  }

  //  给定一个数组 求最大值和最小值    返回对偶元组
  def minAndMax(arr: Array[Int]): (Int, Int) = {

    // 返回值
    val max = arr.max
    val min = arr.min
    (min, max)
  }

  // 数组中小于v、等于v和大于v的元素的个数
  def getNums(arr: Array[Int], v: Int): (Int, Int, Int) = { // 三个值一起返回   用元组

    //
    val maxCount = arr.count(_ > v)
    val equCount = arr.count(_ == v)
    val minCount = arr.count(_ < v)
    (maxCount, equCount, minCount)
  }


  // 数组反转，两两交换 Array(1,2,3,5,6,7,9)  2 1 5 3 7 6 9  to  until 步长  grouped(2)
  def arrSwap(arr: Array[Int]) = {
    for (i <- 0 until arr.length - 1 if (i % 2 == 0)) {
      val tmp = arr(i)
      arr(i) = arr(i + 1)
      arr(i + 1) = tmp
    }
    // 不能少了返回值
    arr
  }

  // 利用 until  可以指定步长
  def arrSwap2(arr: Array[Int]) = {
    for (i <- 0 until(arr.length - 1, 2)) {
      val tmp = arr(i)
      arr(i) = arr(i + 1)
      arr(i + 1) = tmp
    }
    // 不能少了返回值
    arr
  }

  def main(args: Array[String]): Unit = {

    // 是不能访问的
    val s2 = new Students("a1", 30)
    println(s2.sex)

    val arr = Array[Int](10, 20, 3, 56, 78)

    //    println(avg(arr))

    val tp = minAndMax(arr)
    //    println(tp._2)

    //    println(getNums(arr,3))

    //    println(arrSwap(arr).toBuffer)

    //    println(arrSwap2(arr).toBuffer)

    // 按照2个进行分组
    /* val grouped: Iterator[Array[Int]] = arr.grouped(2)

     // Array(10,20)  ==>  Array(20,10)
     val map: Iterator[Int] = grouped.flatMap(t=>t.reverse)
     println(map.toList)
 */
    //
    val d1 = Array(("bj", 28.1), ("sh", 28.7), ("gz", 32.0), ("sz", 33.1))
    val d2 = Array(("bj", 27.3), ("sh", 30.1), ("gz", 33.3))
    val d3 = Array(("bj", 28.2), ("sh", 29.1), ("gz", 32.0), ("sz", 30.5))
    // wordcount   reduceLeft  foldLeft aggreage()()

    // 1 .把数据集合起来  // 并集    项目  --》 需求分析  --》 技术选型（架构）   j2ee  大数据 hadoop spark storm flink  集群
    val data: Array[(String, Double)] = d1 union d2 union d3
    //    d1 ++ d2 ++ d3

    // 在方法外部定义一个函数
    val t = (x: (String, Double)) => x._1
    // 定义第二种函数的方式
    val t2: ((String, Double)) => String = (x) => x._1

    data.groupBy(_._1)
    data.groupBy(t => t._1)
    data.groupBy((x: (String, Double)) => x._1)
    // 匿名函数
    // 显示调用外部的函数（已经存在的）
    data.groupBy(t)
    data.groupBy(t2)


    // 2 要按照 城市分组    k - v
    val grouped: Map[String, Array[(String, Double)]] = data.groupBy(_._1)


    // 3  对value进行处理，统计平均值    得到sum   length
    val result1: Map[String, Double] = grouped.map(t => {
      // Array[(String, Double)]
      val totalTmp = t._2.map(_._2).sum
      val len = t._2.length
      val avgTmp = totalTmp / len
      (t._1, avgTmp)
    })
    //    result1.foreach(println)

    // mapValues   key

    //
    //    foldLeft reduceLeft aggregate
    val result2: Map[String, Double] = grouped.mapValues(t => {
      // 初始值 ： 什么类型的  元素值(String,Double)
//      val totalTmp: Double = t.foldLeft(0d)(_ + _._2) // 总的温度
      //  (A1, A1) => A1
//      (B, A) => B
      val totalTmp: Double = t.foldLeft(0d)((a,b)=> b._2+a) // 总的温度  // double    (String,Double)   Double   double
      val len = t.length // 月份数量
      totalTmp / len
    })
    //    result2.foreach(println)

    // reduce
    val result3 = grouped.mapValues(t => {
      // (String,Double)
      //(A1, A1) => A1 累加值  元素值   (String,Double)
      // (B, A) => B
      val totalTmp: Double = t.reduceLeft((a, b) => ("",a._2+b._2))._2
      val len = t.length
      totalTmp / len // 平均温度
    })

        result3.foreach(println)

    val result4 = grouped.mapValues(t => {
      val totalTmp: Double = t.aggregate(0d)(_ + _._2, _ + _)
      totalTmp / t.length
    })
    result4.foreach(println)


    // 4  可选  排序


  }
}
