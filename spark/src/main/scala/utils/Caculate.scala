package utils

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.commons.lang3.time.FastDateFormat

/**
  * author: sheep.Old 
  * qq: 64341393
  * Created 2018/5/26
  */
object Caculate {
    // 线程不安全的
    // val dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS")

    // 线程安全
    private val dateFormat: FastDateFormat = FastDateFormat.getInstance("yyyyMMddHHmmssSSS")

    def costTime(start: String, end: String) =  {
        val statTime = dateFormat.parse(start).getTime
        val endTime = dateFormat.parse(end).getTime

        endTime - statTime
    }

}
