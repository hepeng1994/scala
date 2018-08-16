package day05.sql;

import day05.been.Student;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class UDF {
    public static void main(String[] args) {
        //创建conf
        SparkConf conf = new SparkConf().setAppName("RDD2DataFrameReflection").setMaster("local");
        //创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        //创建sqlcontext
        SQLContext sqlContext = new SQLContext(sc);

        //rdd转换dateframe
        JavaRDD<String> lines = sc.textFile("E:\\x\\spark新\\spark5\\a.txt");
        JavaRDD<Student> students = lines.map(new Function<String, Student>() {

            @Override
            public Student call(String v1) throws Exception {
                String[] split = v1.split(",");
                Student student = new Student();
                student.setId(Integer.valueOf(split[0].trim()));//trim()去除空格
                student.setName(split[1]);
                student.setAge(Integer.valueOf(split[2].trim()));
                return student;
            }
        });
        //通过反射创造dateset(2.2版本)
        Dataset<Row> studentDF = sqlContext.createDataFrame(students, Student.class);
        //注册一张表
        studentDF.registerTempTable("students");
        // 针对students临时表执行SQL语句，查询年龄小于等于18岁的学生，就是teenageer
        Dataset<Row> teen = sqlContext.sql("select * from students where age<= 18");


        teen.show();
        sc.stop();

    }
}
