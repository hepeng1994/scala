package xiaoniu34.day03

/**
  * Created by Huge
  * Desc: 
  */

object ReduceDemo {

  def main(args: Array[String]): Unit = {
    val arr =Array[String]("hello","spark","hello","tom")

    // 需求： 把拼接成一个字符串

    // 思路1  for循环  拼接


    // reduce  元素归并  元素聚合
    // 是字符串的方法
    println(arr.reduce((a,b)=>a ++ b))
    // 什么时候可以使用_  当变量只被使用了一次的时候，才可以使用
    arr.reduce(_++_)



    // 新的需求： 把 单个的list 聚合  归并
    val lst =Array[List[String]](List("hello"),List("spark"),List("hello"),List("tom"))

    // 思路： 压平  Array[String]
    println(lst.flatten.toList)

    // reduce 实现呢？

    // ++  是list的方法  ::: List的拼接
    lst.reduce((a,b)=> a ++ b)  // List[String]
    lst.reduce((a,b)=> a ::: b)  // List[String]
    lst.reduce((a,b)=> List.concat(a,b))  // List[String]

    lst.reduce(_++_)


    val a1 = Array[Int](1,3,4)
    println(a1.reduceLeft(_+_))  // 求sum 和

    println(a1.reduce(_-_)) // 1 - 3 - 4    (((1)-3)-4)
    println(a1.reduceRight(_-_)) //2     (1-(3-(4)))

    // reduce和reduceLeft
    // op: (A1, A1) => A1      op: (B, A) => B



    /**
      * reduce
      * reduceRight
      */


    // 结论： 在调用reudce方法的时候， 具体能使用什么方法，取决于 集合中元素的类型
    // int  +   String  + ++  List[String ]   ++


    // reduce reduceLeft

    //  fold foldLeft foldRight

    // 多了一个初始值参与运算
    println(a1.fold(0)(_+_))
    println(a1.foldLeft(0)(_-_)) // ((((0) - 1) - 3) - 4)
    println(a1.foldRight(2)(_-_))// (1 - (3 -(4 -(0))))


    // seqop: (B, A) => B, combop: (B, B) => B  调用的是foldLeft

    val lst2:Array[List[Int]] = Array(List(2),List(3,5),List(3,5,7))
    // 累加值的类型是由 初始值的类型决定的   元素值
    println(lst2.aggregate(0d)((a,b)=>math.min(a,b.max),_+_))

    // 并行化集合的时候才会被使用到
   println(lst2.par.aggregate(0d)((a,b)=>math.max(a,b.max),_+_))

    // scala 并行化集合    spark中

  }
}
