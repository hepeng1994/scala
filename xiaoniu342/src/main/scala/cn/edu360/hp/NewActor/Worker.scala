package cn.edu360.hp.NewActor

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

class Worker(masterHost: String, masterPort: Int, cores: Int, memory: Int) extends Actor {

  // Worker 向Maser 注册   拿到master的代理对象，然后向master 发送消息

  // 利用UUID 来生成一个唯一的ID
  val workerId: String = UUID.randomUUID().toString

  var selection: ActorSelection = null

  override def preStart(): Unit = {
    // 4要素
    val path = s"akka.tcp://${Master.MASTER_SYSTEM_NAME}@${masterHost}:${masterPort}/user/${Master.MASTER_ACTOR_NAME}"
    selection = context.actorSelection(path)
    // 向master 的代理对象 发送注册消息
    // 注册哪些资源 wokerId ,cores ,memory   2  to  4 for
    selection ! Register2Master(workerId, cores, memory)
  }

  override def receive: Receive = {
    case RegisteredSuccess => {
      println("worker register to master success")
      // 注册成功之后，就需要启动定时任务  目的： 向master发送心跳信息   workerId  时间？

      /**
        * initialDelay: FiniteDuration,   延迟时间    不需要  0
        * interval:     FiniteDuration,         间隔时间     10s
        * receiver:     ActorRef,               接收者  消息发送给谁   Master 的代理对象
        * message:      Any                     要发送的消息
        */

      // 导入时间单位的包
      import scala.concurrent.duration._

      // 类型不兼容  要求 ActorRef    实际上  ActorSelection

      // self  sender()    ActorRef
      // 先发给自己，然后再 发给master
      // 需要隐式转换
      import context.dispatcher
      println("worker send heart beat to master")

      // 启动定时任务的格式
      context.system.scheduler.schedule(0 seconds, 10 seconds,self, SendHeartBeat(workerId)) // 样例类
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
    val Array(masterHost, masterPort, hostname, port, cores, memory) = args

    val str =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = ${hostname}
         |akka.remote.netty.tcp.port = ${port}
    """.stripMargin


    val conf = ConfigFactory.parseString(str)

    val acs = ActorSystem.create(WORKER_SYSTEM_NAME, conf)

    // 在初始化actor实例的时候， 必须要赋值，
    acs.actorOf(Props(new Worker(masterHost, masterPort.toInt, cores.toInt, memory.toInt)), WORKER_ACTOR_NAME)
  }
}