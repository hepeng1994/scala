package utils


import org.apache.spark.rdd.RDD
import tools.Jpools

/**
  * author: sheep.Old 
  * qq: 64341393
  * Created 2018/5/26
  */
object RptKPIHandler {

    /**
      * 计算业务概况的总订单量，成功订单量，成功订单金额，成功订单时长
      * @param baseData
      */
    def kpiGeneral(baseData: RDD[(String, String, List[Double], String, String)]) = {
        baseData.map(tp => (tp._1, tp._3)).reduceByKey {
            (list1, list2) => list1.zip(list2).map(tp => tp._1 + tp._2)
        }.foreachPartition(partition => {
            // 获取一根连接
            val jedis = Jpools.getJedis
            partition.foreach(tp => {
                jedis.hincrBy(tp._1, "total", tp._2(0).toLong)
                jedis.hincrBy(tp._1, "succ", tp._2(1).toLong)
                jedis.hincrByFloat(tp._1, "money", tp._2(2))
                jedis.hincrBy(tp._1, "time", tp._2(3).toLong)
            })
            jedis.close()
        })
    }


    /**
      * 业务概况 --  没小时的成功订单量及总订单量
      * @param baseData
      */
    def kpiGeneralPerHours(baseData: RDD[(String, String, List[Double], String, String)]) = {
        baseData.map(tp => ((tp._1, tp._2), tp._3.take(2))).reduceByKey {
            (list1, list2) => list1.zip(list2).map(tp => tp._1 + tp._2)
        }.foreachPartition(partition => {
            // 获取一根连接
            val jedis = Jpools.getJedis
            partition.foreach(tp => {
                jedis.hincrBy(tp._1._1, "T-" + tp._1._2, tp._2(0).toLong)
                jedis.hincrBy(tp._1._1, "S-" + tp._1._2, tp._2(1).toLong)
            })
            jedis.close()
        })
    }


    /**
      * 全国各省份充值订单成功数量
      * @param baseData
      */
    def kpiProvinceDistribute(baseData: RDD[(String, String, List[Double], String, String)]): Unit ={
        baseData.map(tp => ((tp._1, tp._4), tp._3(1))).reduceByKey(_ + _)
          .foreachPartition(partition => {
              val jedis = Jpools.getJedis
              partition.foreach(tp => {
                  val pName = ConfigHandler.pcode2name.get(tp._1._2).toString()
                  jedis.hincrBy("P-"+tp._1._1, pName, tp._2.toLong)

              })
              jedis.close()
          })

    }


    /**
      * 每分钟的充值订单及充值金额
      */
    def kpiPerMinutes(baseData: RDD[(String, String, List[Double], String, String)]): Unit ={
        baseData.map(tp => ((tp._1, tp._2, tp._5), List(tp._3(1), tp._3(2)))).reduceByKey {
            (list1, list2) => list1.zip(list2).map(tp => tp._1 + tp._2)
        }
          .foreachPartition(partition => {
              val jedis = Jpools.getJedis
              partition.foreach(tp => {

                  jedis.hincrBy("M-"+tp._1._1, "S-"+tp._1._2+"-"+tp._1._3, tp._2(0).toLong)
                  jedis.hincrByFloat("M-"+tp._1._1, "M-"+tp._1._2+"-"+tp._1._3, tp._2(1))

              })
              jedis.close()
          })
    }

}
