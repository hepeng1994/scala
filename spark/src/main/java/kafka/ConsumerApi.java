package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Author: sheep.Old
 * WeChat: JiaWei-YANG
 * QQ: 64341393
 * Created 2018/6/19
 */
public class ConsumerApi {

    public static void main(String[] args) {


        Properties props = new Properties();
        // key value反序列化
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // kafka 集群所在的位置
        props.setProperty("bootstrap.servers", "xiaoniu1:9092,xiaoniu2:9092,xiaoniu3:9092");

        // 消费者的id
        props.setProperty("group.id", "A-9527");
        /**
         * latest: 最新的
         * earliest: 最早的
         * none:
         */
        props.setProperty("auto.offset.reset", "earliest"); // latest, earliest, none

        // 自动提交偏移量
        props.setProperty("enable.auto.commit", "true"); // __consumer_offsets 特殊的主题下面


        // 消费者客户端
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(props);

        // 指定消费数据的主题（从哪个主题消费数据),可以同时指定多个主题
        kafkaConsumer.subscribe(Arrays.asList("kafka"));

        while (true) {
            // 拉去下来的数据
            ConsumerRecords<String, String> records = kafkaConsumer.poll(3000);

            /**
             * 拉取的消息全局无序
             * 但是在单个分区里面是有序的
             */
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("record = " + record);
            }
        }


    }
}
