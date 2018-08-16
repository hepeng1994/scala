package xiaoniu34.day02.YuXi

import scala.collection.mutable.ListBuffer

/*不可变List 	List 		长度 内容都不可变      默认的
可变List		ListBuffer	 长度内容都是可变的  scala.collection.mutable.ListBuffer  可变的
*/
object ListDemo {
	def main(args: Array[String]): Unit = {
		//不可变list创建
		val list = List[Int](1,2,34,5,6)
		//::  Nil  组成List的两个单位List 是右连接的
		val list1 = 9::5::2::6::Nil
		//map对list集合每一个元组进行操作
		val list2 = list.map(s=>{s+10})
		val list3 = list.map(_*10)
		list2.foreach(println)
		list3.foreach(println)
		//map  切分  压平
		val strings = List[String]("hello word","ni hao","gan ni","lai a")
		val flatten = strings.map(_.split(" ")).flatten
		flatten.foreach(println)
		println("-------------另一种方式与上结果相同--------------")
		val strings1 = strings.flatMap(_.split(" "))
		strings1.foreach(println)
		//可变List--listbuffer
		val ints = ListBuffer[Int](1,2,3)
		//创建一个空的可变列表
		val buffer = new ListBuffer[Int]
		//+=添加一个元素,亦可以添加一组元素
		ints+=(25,36,99,88)
		ints+=11
		//++=添加集合
		ints++=List(101,100)
		//insert是插入(第一个数为角标从哪里插入,后边为要插入得数)
		ints.insert(ints.length-1,56,201,202)
		//remove移除
		ints.remove(ints.length-2)
		ints.clear()
		ints.foreach(println)
		//可变List  通过toList 方法，转换为不可变的Iist
		//toBuffer  会转换为ArrayBuffer
		//转换后的集合都是新的集合，原有的集合不变。
		//一个list 是由两部分的元素组成：头元素  尾列表
		//head  头元素
		//tail  尾列表
		//head===1
		println(list.head)
		//tail====List(2, 34, 5, 6)
		println(list.tail)
		//(2, 34, 5, 6)中的头为2
		println(list.tail.head)

	}
}
