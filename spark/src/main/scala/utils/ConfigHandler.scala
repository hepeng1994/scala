package utils

import java.util

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.Seconds
import scalikejdbc.config.DBs

/**
  * 专门用来解析配置文件的 application.conf
  * author: sheep.Old
  * qq: 64341393
  * Created 2018/5/26
  */
object ConfigHandler extends Serializable {

    // 加载MySQL的配置信息
    DBs.setup()

    private lazy val config: Config = ConfigFactory.load()


    // 充值通知日志类型
    val logType: String = "reChargeNotifyReq"

    // 主题
    val kafkaTopics = config.getString("kafka.topics").split(",")

    // kafka集群所在地址
    val kafkaBrokers = config.getString("kafka.brokers")
    // 消费者组id
    val kafkaGroupId = config.getString("kafka.consumer.groupId")

    // kafka相关参数
    val kafkaParams: Map[String, Object] = Map[String, Object](
        "bootstrap.servers" -> kafkaBrokers,
        "key.deserializer" -> classOf[StringDeserializer],
        "value.deserializer" -> classOf[StringDeserializer],
        "group.id" -> kafkaGroupId,
        "auto.offset.reset" -> "earliest",
        "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    /*-------------------------------redis----------------------------------*/
    val redisHost = config.getString("redis.host")
    val redisPort = config.getInt("redis.port")



    /*----------------------------解析省份编码映射关系-------------------------*/
    val pcode2name: util.Map[String, AnyRef] = config.getObject("pcode2name").unwrapped()


    /**
      * 批次时间间隔
      */
    val duration = Seconds(config.getInt("streaming.batch.duration"))

}
