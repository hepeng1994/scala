package xiaoniu34.day02.YuXi

import scala.collection.mutable.ArrayBuffer

/*数组综述：
不可变数组  Array			长度不可变，内容可变   默认
可变数组 	ArrayBuffer		长度内容都可变		scala.collection.mutable.ArrayBuffer
定长数组  变长数组
*/
object ArrayDemo {
  def main(args: Array[String]): Unit = {
    //定义方法1,类型可以省略,自动推断
    val arr = Array[Int](1,2,6,5,9,45)
    val arr1: Array[Double] = Array(9.5,6933.6,65,6.3,5.3)
    //这种定义方法,必须制定类型和长度
    val arr2 = new Array[Int](5)
    // arr2(0)=100   把角标为零的元素设为100
    arr2(0)=100
    println(arr(0))
    //如何取值
    println(arr(2))
    println("--------------------------ArrayBuffer------------")
    //定义方法1
    val buffer = ArrayBuffer(1,2,5,6,9,85,2,46)
    //定义方法的二
    val buffer2 = new ArrayBuffer[Int]()
    //添加方法
    //添加元素
    buffer +=99999999
    buffer2 +=99999999
    //添加集合
    buffer++=Array(888888,7777777)
    buffer2++=ArrayBuffer(888888,7777777,6666666,1,2,3,5)
    //删除单个或者多个元素
    buffer-=1
    buffer-=(1,2,5)
    buffer2-=1
    buffer2-=(2,3,5)
    //删除集合元素
    buffer--=Array(888888,7777777)
    buffer2--=ArrayBuffer(888888,7777777,6666666)
    //插入元素： 第一个参数是下标，第二个参数是一个可变参数列表，可以支持插入多个值
    //insert(n:Int,ele:Int*)
    buffer.insert(2,222222222)
    buffer2.insert(1,55555555)
    //删除元素：可以直接指定下标，还可以指定下标，和删除的元素个数
    buffer.remove(1,4)
    buffer2.remove(1)
    //清空集合
    buffer.clear()
    //判断是否为空
    println(buffer.isEmpty)
    //打印
    buffer.foreach(print)
    println()
    buffer2.foreach(print)
    buffer.foreach(x=>{println(x)})

    //集合之间的转换：
    //toArray:  ArrayBuffer - Array
    //toBuffer  Array   ArrayBuffer
    //生成新的集合。
      println("------------------数组常用方法------------------------")
    val array = Array(1,25,6,9,8,15,885)
    //最大值
    println(array.max)
    //最小值
    println(array.min)
    //求和
    println(array.sum)
    //排序默认升序
    val sorted = array.sorted
    //降序 元素的反转：reverse
    val reverse = array.sorted.reverse
    reverse.foreach(println)
    //二维数组
    val array6 = Array[Array[Int]](
      Array[Int](1, 25, 69, 38, 56),
      Array[Int](1, 2695, 36, 56,55),
      Array[Int](9,6,8,5,42,55)
    )
    //二维数组遍历
    array6.foreach(_.foreach(println))
    println("---------------------------------------")
    array6.foreach(x=>{x.foreach(println)})
    println("-------------------------------------")
    for (i<-array6;j<-i) println(j)
    //填满数组,但是只能全部相同
    println("---------------------------------------")
    val strings = Array.fill(5)("nihao")
    strings.foreach(println)
  }
}
