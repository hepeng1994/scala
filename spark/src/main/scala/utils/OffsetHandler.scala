package utils


import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010.OffsetRange
import scalikejdbc.{DB, SQL}

/**
  * author: sheep.Old 
  * qq: 64341393
  * Created 2018/5/26
  */
object OffsetHandler {

    /**
      * 存储偏移量信息
      */
    def saveCurrentBatchOffset(offsets: Array[OffsetRange]): Unit = {

        DB.localTx { implicit session =>
            offsets.foreach(osr => {
                SQL("replace into cmcc_offset_31 values(?,?,?,?)")
                  .bind(osr.topic, osr.partition, ConfigHandler.kafkaGroupId, osr.untilOffset)
                  .update().apply()
            })
        }
    }


    /**
      * 从库中获取当前的偏移量信息
      */
    def findCurrentOffsets() = {
        DB.readOnly { implicit sesssion =>
            SQL("select * from cmcc_offset_31 where groupId=? and topics=?")
              .bind(ConfigHandler.kafkaGroupId, ConfigHandler.kafkaTopics.head)
              .map(rs => (
                new TopicPartition(rs.string("topics"), rs.int("partitionId")),
                rs.long("offset")
              )).list().apply()
        }.toMap

    }


}
