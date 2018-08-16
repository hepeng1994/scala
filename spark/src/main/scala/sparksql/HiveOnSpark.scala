package sparksql

import org.apache.spark.SparkContext
import org.apache.spark.sql.{Dataset, SaveMode, SparkSession}

/**
  * Created by Huge 
  * 824203453@qq.com 
  * DATE: 2018/6/27
  * Desc:
  * // HQL 语法
  * 环境配置
  */

object HiveOnSpark {

  def main(args: Array[String]): Unit = {

    // 伪装用户身份
    System.setProperty("HADOOP_USER_NAME", "root")

    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      // 必须开始spark对hive的支持
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    // 查询
   // spark.sql("select * from t_access_times")
    //    .show()

    // 创建表

    // 求每个用户的每月总金额
    //    spark.sql("select username,month,sum(salary) as salary from t_access_times group by username,month")
    // 创建表
        spark.sql("create table t_access1(username string,month string,salary int) row format delimited fields terminated by ','")

    // 删除表
    //    spark.sql("drop table t_access1")

    // 插入数据
    //        spark.sql("insert into  t_access1  select * from t_access_times")
    //    .show()
    // 覆盖写数据
    //    spark.sql("insert overwrite table  t_access1  select * from t_access_times where username='A'")

    // 覆盖load新数据
    //    C,2015-01,10
    //    C,2015-01,20
    //    spark.sql("load data local inpath 't_access_time_log' overwrite into table t_access1")

    // 清空数据
//        spark.sql("truncate table t_access1")
    //      .show()

//    // 写入自定义数据
//    val access: Dataset[String] = spark.createDataset(List("b,2015-01,10", "c,2015-02,20"))
//
//    val accessdf = access.map({
//      t =>
//        val lines = t.split(",")
//        (lines(0), lines(1), lines(2).toInt)
//    }).toDF("username", "month", "salary")
//
//    //    .show()
//
//    accessdf.createTempView("t_ac")
////    spark.sql("insert into t_access1 select * from t_ac")
//
//    // insertInto的api  入库     // database.table
//    accessdf.write.mode(SaveMode.Append).insertInto("t_access1")
//
//    //    new HiveContext(new SparkContext())


    spark.stop()
  }

}
