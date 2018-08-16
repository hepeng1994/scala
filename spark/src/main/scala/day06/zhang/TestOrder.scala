package day06.zhang

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by zhang on 7/29.
  */
//订单
case class tbStock(ordernumber: String, locationid: String, dateid: String) extends Serializable
//订单详情
case class tbStockDetail(ordernumber: String, rownum: Int, itemid: String, number: Int, price: Double, amount: Double) extends Serializable
//日期
case class tbDate(dateid: String, years: Int, theyear: Int, month: Int, day: Int, weekday: Int, week: Int, quarter: Int, period: Int, halfmonth: Int) extends Serializable

object TestOrder {

  def main(args: Array[String]): Unit = {
    // 创建Spark配置
    val sparkConf = new SparkConf().setAppName("MockData").setMaster("local[*]")

    // 创建Spark SQL 客户端
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    //rdd dataframe dataset 一定加隐式转换
    import spark.implicits._
    //读取数据源变成RDD
    val tbStockRdd: RDD[String] = spark.sparkContext.textFile("C:\\it1804班级\\bigdate34\\day05\\资料\\doc\\tbStock.txt")
     //把RDD变成DF
     val tbStockDS = tbStockRdd.map(_.split(",")).map(attr => tbStock(attr(0), attr(1), attr(2))).toDS
    // tbStockDS.show()
     tbStockDS.createTempView("tbStock")

    //-------------------------------------------------
    //订单详情表
    //2）加载数据源
    val tbStockDetailRdd = spark.sparkContext.textFile("C:\\it1804班级\\bigdate34\\day05\\资料\\doc\\tbStockDetail.txt")
    //3）把case和rdd做一个映射
    val tbStockDetailDS = tbStockDetailRdd.map(_.split(",")).map(attr => tbStockDetail(attr(0), attr(1).trim().toInt, attr(2), attr(3).trim().toInt, attr(4).trim().toDouble, attr(5).trim().toDouble)).toDS
    //4）注册成表
    tbStockDetailDS.createTempView("tbStockDetail")
    //spark.sql("select * from tbStockDetail ").show()


    //-------------------------------------------------
    //日期表

   // 2）加载数据源
    val tbDateRdd = spark.sparkContext.textFile("C:\\it1804班级\\bigdate34\\day05\\资料\\doc\\tbDate.txt")

    //3）把case和rdd做一个映射
    val tbDateDS = tbDateRdd.map(_.split(",")).map(attr => tbDate(attr(0), attr(1).trim().toInt, attr(2).trim().toInt, attr(3).trim().toInt, attr(4).trim().toInt, attr(5).trim().toInt, attr(6).trim().toInt, attr(7).trim().toInt, attr(8).trim().toInt, attr(9).trim().toInt)).toDS
   // 4）注册成表
    tbDateDS.createTempView("tbDate")
   // spark.sql("select * from tbStock ").show()
    //spark.sql("select * from tbStockDetail ").show()
    //spark.sql("select * from tbDate ").show()
    /*spark.sql("select C.theyear,COUNT(DISTINCT A.ordernumber) ,SUM(B.amount) from tbStock as A,tbStockDetail as B, tbDate as C " +
      "where A.ordernumber=B.ordernumber AND A.dateid=C.dateid GROUP BY C.theyear ORDER BY C.theyear ").show()
*/
    //spark.sql("SELECT c.theyear, COUNT(DISTINCT a.ordernumber), SUM(b.amount) FROM tbStock a JOIN tbStockDetail b ON a.ordernumber = b.ordernumber JOIN tbDate c ON a.dateid = c.dateid GROUP BY c.theyear ORDER BY c.theyear").show()

    //spark.sql("SELECT theyear, MAX(c.SumOfAmount) AS SumOfAmount FROM (SELECT a.dateid, a.ordernumber, SUM(b.amount) AS SumOfAmount FROM tbStock a JOIN tbStockDetail b ON a.ordernumber = b.ordernumber GROUP BY a.dateid, a.ordernumber ) c JOIN tbDate d ON c.dateid = d.dateid GROUP BY theyear ORDER BY theyear DESC").show()
     //每个产品每年销售的总额
    //spark.sql("SELECT c.theyear, b.itemid, SUM(b.amount) AS SumOfAmount FROM tbStock a JOIN tbStockDetail b ON a.ordernumber = b.ordernumber JOIN tbDate c ON a.dateid = c.dateid GROUP BY c.theyear, b.itemid").show
    //rank() over(partition by theyear order by SumOfAmount desc) rank
    spark.sql("select t.theyear,t.itemid,t.SumOfAmount from (" +
      "SELECT c.theyear, b.itemid, SUM(b.amount) AS SumOfAmount,rank()" +
      " over(partition by c.theyear ORDER BY SUM(b.amount) desc) rank  " +
      "FROM tbStock a JOIN tbStockDetail b ON a.ordernumber = b.ordernumber JOIN tbDate c ON a.dateid = c.dateid " +
      "GROUP BY c.theyear, b.itemid) as t where t.rank =1").show()

    spark.stop()
  }

}
