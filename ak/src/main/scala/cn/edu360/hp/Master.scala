package cn.edu360.hp

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable

/**
  * Created by Huge
  * Desc: 
  */

class Master extends Actor{
  // 所有的worker集合
  val workerMaps = new mutable.HashMap[String,WorkerInfo]()

  // receive 会被执行多次，只要有消息发送过来，就会被执行
  override def receive: Receive = {
    // 用于接收注册的数据
    case Register2Master(workerId,cores,memory)=>{
      println(workerId,cores,memory)

      // 把worker相关信息存储下来，
      workerMaps(workerId)=new WorkerInfo(workerId,cores,memory)

      // 打印
      println(s"add a worker success ,alived workes =${workerMaps.size}")

      // 然后返回注册成功消息
      sender() !  RegisteredSuccess



    }

    case x:String => println(x)
  }
}
object Master{

  val MASTER_SYSTEM_NAME = "master_sys_name"
  val MASTER_ACTOR_NAME = "master_actor_name"
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

    val acs = ActorSystem.create(MASTER_SYSTEM_NAME,conf)

   val master =  acs.actorOf(Props[Master],MASTER_ACTOR_NAME)
    master ! "01 已启动"

  }
}
