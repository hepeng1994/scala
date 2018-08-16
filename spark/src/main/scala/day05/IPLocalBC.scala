package day05

import java.sql.{Connection, DriverManager, PreparedStatement}

import utils.MySpark
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/23
  * Desc:  利用广播变量实现
  */

object IPLocalBC {

  // 根据ip地址获取longIp
  def ip2Long(ip: String): Long = {
    val fragments = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until fragments.length) {
      ipNum = fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

  // 定义一个二分搜索的方法
  def binarySearch(ip: Long, ipRules: Array[(Long, Long, String)]): String = {
    // 两个索引
    var low = 0
    var high = ipRules.length - 1

    while (low <= high) {
      // 取中间索引
      val middle = (low + high) / 2
      // 获取中间索引位置的值
      val (start, end, province) = ipRules(middle)
      // 正好找到位置
      if (ip >= start && ip <= end) {
        return province
      } else if (ip < start) { // 在左区间
        high = middle - 1
      } else {
        low = middle + 1
      }
    }
    // 程序走到这里，没有找到对应的province
    "unknown"
  }

  def main(args: Array[String]): Unit = {

    val sc = MySpark(this.getClass.getSimpleName)

    // 读取数据
    val logs: RDD[String] = sc.textFile("f:/mrdata/ipdata/ipaccess.log")
    val ipData: RDD[String] = sc.textFile("f:/mrdata/ipdata/ip.txt")

    val ipRuleRDD: RDD[(Long, Long, String)] = ipData.map(t => {
      val split = t.split("\\|")
      val start = split(2).toLong
      val end = split(3).toLong
      val province = split(6)
      (start, end, province)
    })

    // RDD不能嵌套操作
    val ipRules: Array[(Long, Long, String)] = ipRuleRDD.collect()

    // 把规则库的数据进行广播
    val broadcast: Broadcast[Array[(Long, Long, String)]] = sc.broadcast(ipRules)

    // 数据切分
    val longIp: RDD[Long] = logs.map(t => {
      val strIp = t.split("\\|")(1)
      // 把ip地址转换成10进制
      ip2Long(strIp)
    })

    // 调用二分搜索来查询省份
    val result: RDD[String] = longIp.map(ip => {
      // 只能保证一个task中共用一份反序列化的数据
      val iPRulesNews:Array[(Long,Long,String)] = broadcast.value
      binarySearch(ip, iPRulesNews)
    })

    // 不再过滤非法值
    val finalRes: RDD[(String, Int)] = result.map((_, 1)).reduceByKey(_ + _)


    // 利用foreachPartition 写mysql
    finalRes.foreachPartition(it => {
      var conn: Connection = null
      var pstmt: PreparedStatement = null
      try {
        // URL
        val url = "jdbc:mysql://localhost:3306/scott?characterEncoding=utf-8"
        val user = "root"
        val passwd = "123"
        // 在生成task的时候，被引用的对象，必须也被序列化发送到executor端。
        conn = DriverManager.getConnection(url, user, passwd)
        // 闭包引用
        pstmt = conn.prepareStatement("insert into access_log values(?,?)") // 不会自动创建表
        // 赋值
        it.foreach(tp => {
          pstmt.setString(1, tp._1)
          pstmt.setInt(2, tp._2)
          pstmt.execute()
        })
      } catch {
        case e: Exception => e.printStackTrace()
      } finally {
        if (pstmt != null) pstmt.close()
        if (conn != null) conn.close()
      }
    })

    sc.stop()
  }

}
