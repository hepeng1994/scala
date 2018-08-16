package day04.test


import java.sql.{Connection, DriverManager, PreparedStatement}
import org.apache.spark.rdd.RDD
import utils.MySpark

object IpSerch {
    // 定义一个方法，把 ip 地址转换为 10 进制
    def ip2Long(ip: String): Long = {
        val fragments = ip.split("[.]")
        var ipNum = 0L
        for (i <- 0 until fragments.length){
            ipNum =  fragments(i).toLong | ipNum << 8L
        }
        ipNum }
    //D定义二分法
    def binarySearch(ip:Long,ipdate:Array[(Long,Long,String)]): String ={
       var low=0
       var up=ipdate.length-1
        while (low<=up){//缺少等于号可能确实很多数据
            val mid=(low+up)/2
            val (start,end,province) = ipdate(mid)
            if(ip>=start&&ip<=end){
                return province
            }else if (ip<start) {
                up=mid-1
            }else{
                low=mid+1
            }
         }
        "unknown"
        }

    def main(args: Array[String]): Unit = {
    val sparkContext = MySpark(this.getClass.getSimpleName)
        //读取log获取ip地址
    val ipLogs = sparkContext.textFile("E:\\x\\spark资料\\spark-04\\作业题\\ipaccess.log")
    val ip = ipLogs.map(str => {
            val stt = str.split("\\|")
            val stc = stt(1)
            ip2Long(stc)
        })
     //读取ip.txt  获取ip范围及省份
    val shuJu = sparkContext.textFile("E:\\x\\spark资料\\spark-04\\作业题\\ip.txt")
        val shuju2 = shuJu.map(str => {
            val stt = str.split("\\|")
            val ipLow = stt(2).toLong
            val ipUp = stt(3).toLong
            val province = stt(6)
            (ipLow, ipUp, province)
        }).collect()

        val province = ip.map(
            binarySearch(_, shuju2)
        )

        // 不再过滤非法值
        val finalRes= province.map((_, 1)).reduceByKey(_ + _)

        // 对结果数据写入到mysql中

        finalRes.foreach(tp => {

            var conn: Connection = null
            var pstmt: PreparedStatement = null
            try {
                // URL
                val url = "jdbc:mysql://localhost:3306/day02?characterEncoding=utf-8"
                val user = "root"
                val passwd = "root"

                conn = DriverManager.getConnection(url, user, passwd)

                val creatPst = conn.prepareStatement("create table if not exists access_log (province varchar(120),cnts int)")
                creatPst.execute()

                pstmt = conn.prepareStatement("insert into access_log values(?,?)") // 不会自动创建表

                // 赋值
                pstmt.setString(1, tp._1)
                pstmt.setInt(2, tp._2)

                pstmt.execute()

            } catch {
                case e: Exception =>  e.printStackTrace()
            } finally {
                if (pstmt != null) pstmt.close()
                if (conn != null) conn.close()
            }
        })

        sparkContext.stop()

    }

}
