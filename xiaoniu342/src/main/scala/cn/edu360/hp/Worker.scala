package cn.edu360.hp

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import cn.edu360.hp.Master.{MASTER_ACTOR_NAME, MASTER_SYSTEM_NAME}
import com.typesafe.config.ConfigFactory

/**
  * Created by Huge
  * Desc:
  * // IDEA   运行时会编译所有的源码
  *
  * akka   actor  怎么代码实现
  * pingpang
  * sparkRpc   master 和worker 的通信
  * 按照流程来编写代码
  *
  * 把定时任务
  *
  *
  *
  */

class Worker(masterHost:String,masterPort:Int,cores:Int,memory:Int) extends Actor {

  // Worker 向Maser 注册   拿到master的代理对象，然后向master 发送消息

  // 利用UUID 来生成一个唯一的ID
   val workerId: String= UUID.randomUUID().toString


  override def preStart(): Unit = {
    // 4要素
    val path =s"akka.tcp://${Master.MASTER_SYSTEM_NAME}@${masterHost}:${masterPort}/user/${Master.MASTER_ACTOR_NAME}"
    val selection: ActorSelection = context.actorSelection(path)
    // 向master 的代理对象 发送注册消息
    // 注册哪些资源 wokerId ,cores ,memory   2  to  4 for
    selection ! Register2Master(workerId,cores,memory)
  }

  override def receive: Receive = {
    case RegisteredSuccess =>{
      println("worker register to master success")
    }
   }
}

object Worker {
  val WORKER_SYSTEM_NAME = "worker_sys_name"
  val WORKER_ACTOR_NAME = "worker_actor_name"

  def main(args: Array[String]): Unit = {
    // 需要有配置参数 k  - v
//    args(0)
    // 一共有6个参数
    val Array(masterHost,masterPort,hostname, port,cores,memory) = args

    val str =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = ${hostname}
         |akka.remote.netty.tcp.port = ${port}
    """.stripMargin


    val conf = ConfigFactory.parseString(str)

    val acs = ActorSystem.create(WORKER_SYSTEM_NAME, conf)

    // 在初始化actor实例的时候， 必须要赋值，
    acs.actorOf(Props(new Worker(masterHost,masterPort.toInt,cores.toInt,memory.toInt)), WORKER_ACTOR_NAME)
  }
}