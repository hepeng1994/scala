package sparksql

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc:
  * join
  * 多个rdd
  */

object JoinDemo {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()

    import spark.implicits._

    // userInfo name age sex pcode
    val user: Dataset[String] = spark.createDataset(List("dingding 18 F henan", "dangdang 16 F henan", "dongdong 28 M shandong",
      "laozhao 30 M heilongjiang", "cangls 36 F jp","meixi 30 M agt"))

    // province pcode pname
    val province: Dataset[String] = spark.createDataset(List("henan,河南省", "shandong,山东省", "heilongjiang,黑龙江", "jp,日本省", "tw,台湾省"))


    // 对数据进行切分
    val userSplit: Dataset[Row] = user.map(str => {
      val split = str.split(" ")
      (split(0), split(1).toInt, split(2), split(3))
    }).toDF("name", "age", "gender", "pcode")
    val proSplit: Dataset[Row] = province.map(str => {
      val split = str.split(",")
      (split(0), split(1))
    }).toDF("pcode", "province")

    // SQL
    // 注册2个临时视图  关联查询
    userSplit.createTempView("v_user")
    proSplit.createTempView("v_pro")

//    val result1: DataFrame = spark.sql("select * from v_user t1 join v_pro t2 on t1.pcode = t2.pcode ")
//    result1.show()

    //DSL join  添加条件    判断的标准是 ===
    userSplit.join(proSplit).where(userSplit("pcode") === proSplit("pcode")) // .show()

    userSplit.join(proSplit,userSplit("pcode") === proSplit("pcode"),"right").show()

    // 如果参与join的字段名称一致 可以直接使用一个
    userSplit.join(proSplit,"pcode")// .show()

    // 默认使用的是inner join
    spark.stop()
  }
}
