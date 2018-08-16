package cn.edu360.hp

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by Huge
  * Desc:  hello actor  示例类
  */

class HelloActor extends Actor {

  // 重写receive方法
  // PartialFunction[Any, Unit]   接收任意的类型，返回值类型为Unit   接收消息，然后处理
  override def receive: Receive = {

    // 接收消息
    case  "hello actor " => println("hello actor")
//    case x:String => println(x)
  }
}

object HelloActor {
  // 创建 actorSystem 的名称  和 actor的名称
  val ACTOR_SYSTEM_NAME = "helloActor_sysName"
  val ACTOR_NAME = "helloActor_name"

  def main(args: Array[String]): Unit = {

    // 需要有配置参数 k  - v

    // 通过模式匹配来接收 main方法的参数
    val Array(hostname, port) = args

    val str =
      s"""
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname = ${hostname}
    |akka.remote.netty.tcp.port = ${port}
    """.stripMargin

    // 通过参数来获取一个配置的对象
    val conf:Config = ConfigFactory.parseString(str)

    // actor 由actorSystem来创建的
    val acs: ActorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME, conf)

    // 根据HelloActor 创建一个该actor的代理对象
    // 根据 HelloActor类型，反射生成该类型的代理对象
    val proxy: ActorRef = acs.actorOf(Props[HelloActor],ACTOR_NAME)

    // 只能通过代理对象来发送消息   发送消息的写法： 代理对象  ！  消息内容

    proxy ! "hello actor"
  }
}
