package day05.sql
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession



object kaichaung {
    case class Score(name: String, classId: Int, score: Int)
        def main(args: Array[String]): Unit = {
            val sparkConf = new SparkConf().setAppName("score").setMaster("local[*]")
            val sparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

            import sparkSession.implicits._
            val scoreDF = sparkSession.sparkContext.makeRDD(Array(Score("a1", 1, 80),
                Score("a2", 1, 78),
                Score("a3", 1, 95),
                Score("a4", 2, 74),
                Score("a5", 2, 92),
                Score("a6", 3, 99),
                Score("a7", 3, 99),
                Score("a8", 3, 45),
                Score("a9", 3, 55),
                Score("a10", 3, 78))).toDF("name", "class", "score")
            scoreDF.createOrReplaceTempView("score")

//            sparkSession.sql("select name, class, score, count(name) over() name_count from score").show()
//
//            sparkSession.sql("select name, class, score, count(name) over(partition by class) name_count from score").show()
//            sparkSession.sql("select name, class, score, count(name) over(partition by score) name_count from score").show()
//
//            sparkSession.sql("select name, class, score, row_number() over(partition by class order by score) rank from score").show()
//            sparkSession.sql("select name, class, score, rank() over(partition by class order by score) rank from score").show()
//            sparkSession.sql("select name, class, score, dense_rank() over(partition by class order by score) rank from score").show()
//            sparkSession.sql("select name, class, score, ntile(6) over(partition by class order by score) rank from score").show()

            println("===========================")
            sparkSession.sql("select name, class, score, row_number() over(order by score) rank from score").show()
            sparkSession.sql("select name, class, score, rank() over(order by score) rank from score").show()
            sparkSession.sql("select name, class, score, dense_rank() over(order by score) rank from score").show()
            sparkSession.sql("select name, class, score, ntile(6) over(order by score) rank from score").show()


            println("=======================求每个班最高成绩学生的信息groupBy=======================")
            sparkSession.sql("select class, max(score) max from score group by class").show()
            sparkSession.sql("select a.name, a.class, b.max from score a, " +
              "( select class, max(score) max from score group by class) as b " +
              "where a.score = b.max").show()

            println("=======================求每个班最高成绩学生的信息rank=======================")
            println("===============================使用开窗函数===============================")
            sparkSession.sql("select name,class,score,rank() over(partition by class order by score desc) rank from score").show()

            println("=================================取最高分数=================================")
            sparkSession.sql("select * from " +
              "( select name,class,score,rank() over(partition by class order by score desc) rank from score) as t " +
              "where t.rank=1").show()

            println("=======================求每个班最高成绩学生的信息row_number=======================")
            println("=================================使用开窗函数================================")
            sparkSession.sql("select name,class,score,row_number() over(partition by class order by score desc) rank from score").show()

            println("=================================使用开窗函数max================================")
            sparkSession.sql("select name,class,score,max(score) over(partition by class order by score desc) rank from score").show()

            println("=================================使用开窗函数max================================")
            sparkSession.sql("select name,class,score,sum(score) over(partition by class order by score desc) rank from score").show()

        }
}
