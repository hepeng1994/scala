package utils

import java.sql.{Connection, DriverManager, PreparedStatement}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/24
  * Desc:  专门存放 方法
  */

object IpUtils {
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


  val binarySearch2 = (ip: Long, ipRules: Array[(Long, Long, String)]) => {
    // 两个索引
    var low = 0
    var high = ipRules.length - 1

    // 定义两个变量 进行判断
    var result = "unknown"
    var flag = true
    while (low <= high && flag) {
      // 取中间索引
      val middle = (low + high) / 2
      // 获取中间索引位置的值
      val (start, end, province) = ipRules(middle)
      // 正好找到位置
      if (ip >= start && ip <= end) {
        result = province
        flag = false
      } else if (ip < start) { // 在左区间
        high = middle - 1
      } else {
        low = middle + 1
      }
    }
    result
  }


  // 提取写入mysql的函数
  val data2Mysql = (it: Iterator[(String, Int)]) => {
    var conn: Connection = null
    var pstmt: PreparedStatement = null
    try {
      // URL
      val url = "jdbc:mysql://192.168.11.156:3306/day02?characterEncoding=utf-8"
      val user = "root"
      val passwd = "root"
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
  }
}
