package report

import com.alibaba.fastjson.JSON
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import utils.{Caculate, ConfigHandler, OffsetHandler, RptKPIHandler}

/**
  * 实时计算的主类
  * author: sheep.Old 
  * qq: 64341393
  * Created 2018/5/26
  */
object RtpApplication {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf()
        sparkConf.setMaster("local[*]")
        sparkConf.setAppName("中国移动实时运营监控")
        sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        sparkConf.set("spark.streaming.stopGracefullyOnShutdown", "true")
        //        sparkConf.set("spark.streaming.kafka.maxRatePerPartition", "1000")

        val ssc = new StreamingContext(sparkConf, ConfigHandler.duration)

        val stream = KafkaUtils.createDirectStream(ssc,
            LocationStrategies.PreferConsistent,
            ConsumerStrategies.Subscribe[String, String](
                ConfigHandler.kafkaTopics,
                ConfigHandler.kafkaParams,
                OffsetHandler.findCurrentOffsets()
            )
        )

        stream.foreachRDD(rdd => {

            if (!rdd.isEmpty()) {

                // 获取该批次的偏移量信息
                val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

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

                // 存储偏移量
                OffsetHandler.saveCurrentBatchOffset(offsetRanges)

            }

        })

        ssc.start()
        ssc.awaitTermination()

    }


}
