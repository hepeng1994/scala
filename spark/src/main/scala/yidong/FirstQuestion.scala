package yidong

import com.alibaba.fastjson.JSON
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import utils.{Caculate, ConfigHandler, DBHepler, RptKPIHandler}





object FirstQuestion {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]")
        // 设置rdd 可以被压缩的
        sparkConf.set("spark.rdd.compress", "true")
        // 设置spark rdd的序列化
        sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        // 缓慢的关闭jvm,而不是立即停止
        sparkConf.set("spark.streaming.stopGracefullyOnShutdown", "true")
        // 设置每个分区每分钟可以获取的最大记录条数
        sparkConf.set("spark.streaming.kafka.maxRatePerPartition", "10000")
        /**
          * 批次时间的设置，的依据你的计算逻辑、集群的计算资源
          *
          * batch duration  >= total delay
          */
        val ssc = new StreamingContext(sparkConf, Seconds(2)) // 2 * 10000 * 3


        implicit val groupId = "C-222"
        val kafkaParams = Map[String, Object](
            "bootstrap.servers" -> "xiaoniu1:9092,xiaoniu2:9092,xiaoniu3:9092",
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> groupId,
            "auto.offset.reset" -> "earliest",
            "enable.auto.commit" -> (false: java.lang.Boolean) // 0.8 self __consumer_offsets
        )

        val topics = Array("flume")


        /**
          * 读取MySQL偏移量信息封装成一个Map[TopicPartition, Long]
          */
        val dbOffsets = DBHepler.getOffset4MySQL(groupId, topics)
        //从kafka中读取数据
        val stream = KafkaUtils.createDirectStream(ssc,
            LocationStrategies.PreferConsistent, // 如果executor和kafka的broker是同一台节点，设置PreferBrokers
            ConsumerStrategies.Subscribe[String, String](topics, kafkaParams, dbOffsets)
        )


        stream.foreachRDD(rdd => {
            // 获取偏移量 driver, 数组的size是多少呢？和kafka分区是一样的
            // 获取偏移量一定是对一手的rdd进行获取
            val ranges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
            // 将json字符串转成JSONObject
            val filtered = rdd.map(crd => JSON.parseObject(crd.value()))
              // 过滤出符合要求的日志类型
              .filter(_.getString("serviceName").equalsIgnoreCase(ConfigHandler.logType))
            val baseData = filtered.map(json => {
                val startTime = json.getString("requestId").substring(0, 17) // yyyyMMddhhmmssSSS
                val endTime = json.getString("receiveNotifyTime")
                val fee = json.getDouble("chargefee")
               val costTime = Caculate.costTime(startTime, endTime)
                val result: (Double, Double, Double) = if (json.getString("bussinessRst").equals("0000")) (1, fee, costTime) else (0, 0, 0)

                // 数据当前日期
                val day = startTime.substring(0, 8)
                val hour = startTime.substring(8, 10)
                val minutes = startTime.substring(10, 12)

                // 省份编码
                val pCode = json.getString("provinceCode")


                // (当前日期， List(总的，成功的，钱，时长))
                (day, hour, List[Double](1, result._1, result._2, result._3), pCode, minutes)
            }).cache()

            RptKPIHandler.kpiGeneral(baseData)
            RptKPIHandler.kpiGeneralPerHours(baseData)
            RptKPIHandler.kpiProvinceDistribute(baseData)
            RptKPIHandler.kpiPerMinutes(baseData)
            /**
              * 将本批次的偏移量插入到MySQL
              */
            DBHepler.saveCurrentOffset2MySQL(ranges)
        })

        ssc.start()
        ssc.awaitTermination()
    }

}
