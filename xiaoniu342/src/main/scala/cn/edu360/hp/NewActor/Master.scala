package cn.edu360.hp.NewActor

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable

/**
  * Created by Huge
  * Desc: 
  */

class Master extends Actor {

  // 把时间单独提取出来
  val  OutOffTime =  2 * 10 * 1000   // 10 心跳时间
  // 所有的worker集合
  val workerMaps = new mutable.HashMap[String, WorkerInfo]()


  // 在preStart方法中，启动定时任务，检测超时的worker    10s
  override def preStart(): Unit = {

    import context.dispatcher
    import  scala.concurrent.duration._
    // 发送什么消息？   case object
    context.system.scheduler.schedule(0 seconds,15 seconds,self, CheckWorkerStatus)
  }

  // receive 会被执行多次，只要有消息发送过来，就会被执行
  override def receive: Receive = {
    // 用于接收注册的数据 
    case Register2Master(workerId, cores, memory) => {
      println(workerId, cores, memory)

      // 把worker相关信息存储下来，
      workerMaps(workerId) = new WorkerInfo(workerId, cores, memory)

      // 打印
      println(s"add a worker success ,alived workes =${workerMaps.size}")

      // 然后返回注册成功消息
      sender() ! RegisteredSuccess
    }
    case "master" => println("success")
    case x: String => println(x)

    case SendHeartBeat(workerId) => {
      // master  收到worker心跳信息之后，做什么事 ？     改时间， worker的心跳时间

      // 从map中获取workerId
      val workerInfo = workerMaps(workerId)
      // 把最后一次心跳时间，修改为当前时间
      workerInfo.lastHeartBeatTime = System.currentTimeMillis()
      println("update worker heart beat time success")
    }

      // 检测超时的worker  把超时的worker 从worker列表中删除
    case CheckWorkerStatus =>{
       // 定规则： 连续2 次没有发送心跳，挂掉了  9 10 11 12   12 - 9 > 2 个小时
      // 遍历查找(过滤)  map集合，当前时间 - 最后一次心跳时间  >   2 次 心跳间隔的时间     找到的结果数据，就是已经挂掉的worker
      // 把这些worker 从 map集合中删除掉

      // 不能一边遍历集合，一边删除
      val deadWorkers: mutable.HashMap[String, WorkerInfo] = workerMaps.filter(t => {
        // 当前时间
        val currTime = System.currentTimeMillis() // ms
        // 心跳时间
        val heartTime = t._2.lastHeartBeatTime
        // 时间判断
        currTime - heartTime > OutOffTime
      })

      // 从workersMap中删除 超时的worker
//      workerMaps --= deadWorkers.map(_._1)

      //  根据workerId 来执行删除  根据 key 来删除元素
      deadWorkers.map(t=>workerMaps -= t._1)

      println(s"current live worker =${workerMaps.size}")
    }
  }
}

object Master {

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

    val acs = ActorSystem.create(MASTER_SYSTEM_NAME, conf)

    val master = acs.actorOf(Props[Master], MASTER_ACTOR_NAME)
    master ! "01 已启动"

  }
}
