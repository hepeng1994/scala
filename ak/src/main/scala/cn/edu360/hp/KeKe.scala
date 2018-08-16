package cn.edu360.hp

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by Huge
  * Desc: 
  */

class KeKe extends Actor{

  var proxy: ActorSelection = null
  // 在类实例化之后，立即执行   而且仅执行一次
  override def preStart(): Unit = { // 最佳获取Go代理对象的位置
    println("run prestart  method")
    // 4个要素 1 ，协议   2，actorSystemName   3,地址和端口   4，actor的名称
    val path = s"akka.tcp://${AlphaGo.AG_SYSTEM_NAME}@127.0.0.1:7777/user/${AlphaGo.AG_ACTOR_NAME}"
    // actorSelection  也是一个代理对象
   proxy = context.actorSelection(path)

    proxy ! "发球"
  }

  // 重写方法
  override def receive: Receive = {

    case "回球" => {
      println("run receive method")

      sender() ! "发球"
    }
  }
}

object KeKe{
  val KEKE_SYSTEM_NAME = "keke_sys_name"
  val KEKE_ACTOR_NAME = "keke_actor_name"
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

    val conf = ConfigFactory.parseString(str)

    // 获取到了一个actorSystem
    val acs = ActorSystem.create(KEKE_SYSTEM_NAME,conf)

    // 到这里，该actor就已经创建完成
    acs.actorOf(Props[KeKe],KEKE_ACTOR_NAME)

    // 怎么拿到go的代理对象


  }
}