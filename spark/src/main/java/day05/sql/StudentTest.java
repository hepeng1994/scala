package day05.sql;

import day05.been.Student;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class StudentTest {
    public static void main(String[] args) {
        //创建conf
        SparkConf conf = new SparkConf().setAppName("RDD2DataFrameReflection").setMaster("local");
        //创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        //创建sqlcontext
        SQLContext sqlContext = new SQLContext(sc);

        //rdd转换dateframe
        JavaRDD<String> lines = sc.textFile("E:\\x\\spark新\\spark5\\a.txt");
        JavaRDD<Row> students = lines.map(new Function<String, Row>() {

            @Override
            public Row call(String v1) throws Exception {
                String[] split = v1.split(",");
                return RowFactory.create(
                        Integer.valueOf(split[0]),
                        split[1],
                        Integer.valueOf(split[2])
                );

            }
        });
        List<StructField> structFileds = new ArrayList<>();
        structFileds.add(DataTypes.createStructField("id",DataTypes.IntegerType,true));
        structFileds.add(DataTypes.createStructField("name",DataTypes.StringType,true));
        structFileds.add(DataTypes.createStructField("age",DataTypes.IntegerType,true));
        //通过反射创造dateset(2.2版本)
        StructType structType = DataTypes.createStructType(structFileds);
        Dataset<Row> dataFrame = sqlContext.createDataFrame(students, structType);

        //注册一张表
        dataFrame.registerTempTable("students");
        // 针对students临时表执行SQL语句，查询年龄小于等于18岁的学生，就是teenageer
        Dataset<Row> teen = sqlContext.sql("select * from students where age<= 18");
        teen.show();
        sc.stop();

    }
}
