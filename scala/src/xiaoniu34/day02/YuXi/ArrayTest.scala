package xiaoniu34.day02.YuXi
//练习一： 给定一个Array[Int]的数组，求 平均值
//练习二： 给定一个Array[Double]的数组，把最大值和最小值一起返回。
object ArrayTest {
	def avg(array: Array[Int]):Double={
		 array.sum/array.length*1.0
	}
	def  maxMin(array: Array[Double]):(Double,Double)={
		(array.max,array.min)
	}
	def main(args: Array[String]): Unit = {
	//练习一
	val array = Array(1,2,3,4,5,6,7)
		val i = array.sum/array.length*1.0
		println(i)
	//练习二
	val doubles = Array[Double](2.5,6.3,9.9,88.3)
		println(doubles.max,doubles.min)
	//用方法完成
		//练习一
		println(avg(array))
		println(maxMin(doubles))
	}
}
