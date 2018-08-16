package sparksql

import java.net.URL

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc: 
  */

object TopK {
  val topK = 5

  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import session.implicits._

    val file = session.read.textFile("E:\\x\\spark资料\\spark-02\\spark-02\\作业\\teacher.log")
    val st: DataFrame = file.map({
      t =>
        val index = t.lastIndexOf("/")
        // 截取字符串
        val tName = t.substring(index + 1)
        // 封装数据为URL，然后获取Host内容
        val uri = new URL(t.substring(0, index))
        val host = uri.getHost

        val hostArray = host.split("[.]") // 要对特殊的字符进行转义
      // 获取学科名称
      val sub = hostArray(0)
        // 返回元组
        (sub, tName)
    }).toDF("subject", "teacher")

    st.createTempView("t_sub_teacher")

    // 该学科下的老师的访问次数
   val sql = session.sql("select subject,teacher,count(*) cnts from t_sub_teacher group By subject,teacher ") //order by cnts desc
    //    sql

    //      sql.show()

    // 全局topK
    //    sql.limit(3).show()
    sql.createTempView("v_tmp")

    // 求分组topK  分学科的老师 排序
    //    val groupedTop = session.sql("select subject,teacher,cnts,row_number() over(partition by subject order by cnts desc) sub_order from v_tmp  order by cnts desc")
    //    groupedTop.show()
    // 分学科的排序 取topK
    //    val groupedTop = session.sql(s"select * from (select subject,teacher,cnts,
    // row_number() over(partition by subject order by cnts desc) sub_order
    // from v_tmp order by cnts desc ) where sub_order <= $topK")

    //  分学科的排序  + 全局排序
    //    val groupedTop = session.sql("select subject,teacher,cnts,
    // row_number() over(partition by subject order by cnts desc) sub_order,
    // row_number() over(order by cnts desc) g_order
    // from v_tmp  order by cnts desc")

    // 分学科的TopK 排序 + 全局排序  rank 支持并列排序
//    val groupedTop = session.sql("select * from (select subject,teacher,cnts," +
//      "row_number() over(partition by subject order by cnts desc) sub_order," +
//      "rank() over(order by cnts desc) g_order " +
//      s"from v_tmp   ) where sub_order <=$topK")
//    groupedTop.show()

    // 全局排序，分学科topk排序，选中的全局排序（可使用row_number over()  rank() over ()  dense_rank()）
       val groupedTop = session.sql("select * ,dense_rank() over(order by cnts desc) choose_num from (select subject,teacher,cnts," +
         "row_number() over(partition by subject order by cnts desc) sub_order," +
         "rank() over(order by cnts desc) g_order " +
         s"from v_tmp) where sub_order <=$topK")

        groupedTop.show()

    // 释放资源
    session.close()
  }
}
