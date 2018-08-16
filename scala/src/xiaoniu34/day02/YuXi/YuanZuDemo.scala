package xiaoniu34.day02.YuXi
/*scala中把集合框架分为3类：
序列 Seq ；集 Set  ；映射 Map   Iterable

每一类的集合又分为两类： 依据包
不可变集合 	scala.collection.immutable   默认的 不需要导入
可变集合 	scala.collection.mutable    必须手动导包
*/
object YuanZuDemo {
  def main(args: Array[String]): Unit = {
    //Tuple  是不同元素的集合。 是不可变的。
    //1，	直接使用括号，在括号中封装数据（）
    //2，	new Tuple
    val tp =(1,2,"你好",656)
    //new Tuple 时可以选择参数个数,Tuple后是几参数就是几个,最多22个
    val tp1=new Tuple3(1,"年和平",11)
    //元组角标从开始
    println(tp _1)
    println(tp1 _3)
    //val tp2 = (“name”,age)   Map中的每一个元素，都是一个对偶元组
    //对偶元组中的方法： 交换元素  seap
    val tp3 =("zuiliang","buguorenxin")
    val swap = tp3.swap
    //println(tp3 _1,tp3 _2)   与toString方法一样  打印
    println(tp3.toString())
    println(swap)
    //拉链操作
    println("----------------------拉链操作-----------------------")
    val value = Array(1,25,6,97,8,7)
    val array = Array("nihao","zugu","renxin")
    val strings = Array("haha","xixi","gaga")
    // zip 拉链操作，支持多个数据集的 继承，多个数组的zip，元素类型是嵌套元组
    //a.zip(b)  以a数组元素为key,b数组元素为vlaue进行重新组装(元组)封装称为新的数组
    val tuples = value.zip(array)
    //(1,nihao)(25,zugu)(6,renxin)
    val tuples1 = value.zip(array).zip(strings )
    //((1,nihao),haha)((25,zugu),xixi)((6,renxin),gaga)
    tuples.foreach(print)
    println()
    tuples1.foreach(print)
    println()
    // zipWithIndex  索引从0 开始  生成的数据类型是 元组
    //((1,nihao),0)((25,zugu),1)((6,renxin),2)
    val index = tuples.zipWithIndex
    index.foreach(print)
    println()
  }
}
