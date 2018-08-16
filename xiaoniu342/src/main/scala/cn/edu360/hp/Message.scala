package cn.edu360.hp

/**
  * Created by Huge
  * Desc:  用于存放所有的样例类和样例对象
  */

object Message {

}

// 创建一个样例类，用于封装 worker的注册信息
case class Register2Master(workerId:String,cores:Int,memory:Int)

// 用于接收 worker返回成功的消息
case object RegisteredSuccess