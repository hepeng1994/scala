package cn.edu360.hp

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by Huge
  * Desc:
  * 先启动
  */

// 导包 alt + Enter   重写抽象方法  ctrl + i   重写方法  ctrl + o
class AlphaGo extends Actor{

  //  启动后的监听地址  akka.tcp://go_sys_name@127.0.0.1:7777
  override def receive: Receive = {
    // 接收消息
    case "发球" => {
      // 需要进行消息回复
       // self 自己的代理对象，给自己回复消息
      Thread.sleep(1000)
      println("go 接球")
      sender() ! "回球"   // 用于给其他人回复消息
    }
  }
}

object AlphaGo{
  val AG_SYSTEM_NAME = "go_sys_name"
  val AG_ACTOR_NAME = "go_actor_name"
  def main(args: Array[String]): Unit = {

    // 通过模式匹配来接收 main方法的参数
    val Array(hostname, port) = args

    val str =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = ${hostname}
         |akka.remote.netty.tcp.port = ${port}
    """.stripMargin

    val conf = ConfigFactory.parseString(str)

    val acs = ActorSystem.create(AG_SYSTEM_NAME,conf)

    val ac1 = acs.actorOf(Props[AlphaGo],AG_ACTOR_NAME)

    // 发球

    ac1 ! ""
  }
}