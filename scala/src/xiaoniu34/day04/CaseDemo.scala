package xiaoniu34.day04

/**
  * Created by Huge
  * Desc: 
  */

object CaseDemo {

  def main(args: Array[String]): Unit = {

    val p = new Person() // scala中没有static
    val p2 = new Person() // scala中没有static

    // case 默认重写了equals和hashcode方法


//    val b = new Boy()
    val b2 = Boy() // Boy是一个类  对象()-->  apply

//    b.getAge()
    b2.getAge()

    println(b2.name)

    println(p == p2) // false

//    println(b == b2) // true

  }
}

// case class  多例  模式匹配   特殊
case class Boy(){

  val name="zxy"

  def getAge(): Unit ={
    println(50)
  }
}


// 作为一个消息 单例的   不需要封装数据
case object CheckStatus

// 定时任务
//
// hadoop hdfs集群 有哪些角色  namenode  和 datanode

// namenode 启动之后，心跳检测机制

// 定时任务 10s   发送一个case object 提示， 去检测datanode的状态

// Spark  Master Worker 的 rpc通信  case  class  case  object