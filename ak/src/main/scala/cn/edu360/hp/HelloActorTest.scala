package cn.edu360.hp

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

class HelloActorTest extends Actor{
    override def receive: Receive = {
        case "hello actor"=>println("hello actor")
    }
}
object HelloActorTest {
        // 创建 actorSystem 的名称  和 actor的名称
        val ACTOR_SYSTEM_NAME="helloActor_sysName"
        val ACTOR_NAME ="helloActor_name"
    def main(args: Array[String]): Unit = {
        //需要配置k-v
        //透过匹配模式来接受main方法的参数
        //val Array(hostname,port)=args
        val str=
            """
              |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
              |akka.remote.netty.tcp.hostname = "127.0.0.1"
              |akka.remote.netty.tcp.port = "8888"
            """.stripMargin
        //通过参数来获取一个配置对象
        val config = ConfigFactory.parseString(str)
        // actor 由actorSystem来创建的
        val actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME,config)
        // 根据HelloActor 创建一个该actor的代理对象
        // 根据 HelloActor类型，反射生成该类型的代理对象
        val proxy = actorSystem.actorOf(Props[HelloActorTest],ACTOR_NAME)
        // 只能通过代理对象来发送消息   发送消息的写法： 代理对象  ！  消息内容
        proxy ! "hello actor"


    }
}

