package xiaoniu34.day01.xin
/**
  * 1.分号可以自动推动,如果一行写多行语句，要加分号
  * 2.var定义变量，val定义常量
  * 3.class是可以传参 的，这就相当于构造方法。属性没有加访问修饰符，public
  * 4.构造方法可以重载，第一行调用默认的构造方法
  * 5.object,相当于java中的单例，里面都是静态的.不可以传参
  * 6.伴生类和伴生对象.互相访问对方 的私有成员
  */
class Person(xname:String,xage:Int) {
    private var name=xname
    var age=xage
    var gender='m'
    println("---------------")
    //构造方法重载
    def this (xname:String,xage: Int,xgender:Char){
        this(xname,xage)
        this.gender=xgender
    }
    //get方法
    def getName():String={
        name
    }
}
object Person {
    def main(args: Array[String]): Unit = {
        val i=10
        var j=10
        j=20
       println(i)
        var person=new Person("zhangsan",20,'f')
        println(person.name)
    }
}
