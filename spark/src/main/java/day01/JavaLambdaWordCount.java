package day01;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Huge
 * DATE: 2018/6/14
 * Desc:
 * java lambda 表达式的wordcount
 */

public class JavaLambdaWordCount {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf();
        // java的程序 一定是使用JavaAPI 去实现

        conf.setMaster("local[*]");
        conf.setAppName(JavaLambdaWordCount.class.getSimpleName());

        JavaSparkContext sc = new JavaSparkContext(conf);

        // 读数据
        JavaRDD<String> lines = sc.textFile(args[0]);

        // t =>  t ->
        JavaRDD<String> words = lines.flatMap(t -> Arrays.asList(t.split(" ")).iterator());

        JavaPairRDD<String, Integer> wordAndOne = words.mapToPair(word -> new Tuple2<>(word, 1));

        JavaPairRDD<String, Integer> result = wordAndOne.reduceByKey((a, b) -> a + b);

        JavaPairRDD<Integer, String> beforeSwap = result.mapToPair(tp -> tp.swap());

        JavaPairRDD<Integer, String> sorted = beforeSwap.sortByKey(false);

        JavaPairRDD<String, Integer> finalRes = sorted.mapToPair(tp -> tp.swap());

        finalRes.saveAsTextFile(args[1]);

        sc.stop();
    }
}
