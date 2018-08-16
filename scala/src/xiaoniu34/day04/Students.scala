package xiaoniu34.day04

/**
  * Created by Huge
  * Desc:
  * 成员属性的访问权限
  * 默认的： public
  * private 只能在伴生对象中访问，其他地方不能访问
  * private [this] 只能在当前类中使用，伴生对象中无效
  * private [具体的包]
  *
  * 成员方法的访问权限：
  * 普通方法  辅助构造器  主构造器
  * 默认的： public
  * private
  * private [this]
  *
  * private [包名]  指定包及其子包范围内有效  包名必须是单个包名，包名只能是全局路径中的一个子包名
  *
  * 类的访问权限
  * 默认： public
  * private  [this] 在当前包及其子包范围内有效
  *
  *private [指定包]   指定包及其子包范围内有效
  */

private [xiaoniu34] class Students ( val name:String,private val age:Int){

  // 如果想改成私有的，加private 关键字
  // 包名 不能写全局的路径，只能写单个  包名是属于当前包的全局路径中的一个
  // 在指定包及其子包范围内有效
   private [xiaoniu34] val sex ="man"

  // 普通方法
  private def getAge()={
    println(age+10)
    age + 10
  }

   def this()={
    this("mijie",30)
  }
}
object Students{

  def main(args: Array[String]): Unit = {

    val s1 = new Students("",12)
//    println(s1.sex)
    println(s1.age)
    s1.getAge()
  }
}

object Test2{
  def main(args: Array[String]): Unit = {
    // 加上private之后，虽然编译通过，但是运行报错
    val s1 = new Students("",12)
    // 类 中私有的属性，不能访问了
//    println(s1.sex)
//    println(s1.age)
//    s1.getAge()
  }
}

