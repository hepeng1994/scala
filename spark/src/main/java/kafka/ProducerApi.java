package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.UUID;

/**
 * Author: sheep.Old
 * WeChat: JiaWei-YANG
 * QQ: 64341393
 * Created 2018/6/19
 */
public class ProducerApi {

    public static void main(String[] args) throws InterruptedException {

        // 封装Producer参数的
        HashMap<String, Object> configs = new HashMap<String, Object>();
        // kafka计算所在位置
        configs.put("bootstrap.servers", "xiaoniu1:9092,xiaoniu2:9092,xiaoniu3:9092");

        // key value的序列化类
        configs.put("key.serializer", StringSerializer.class);
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        /**
         * acks 应答参数设置：
         *    all|-1: follower -> leader -> producer
         *    0: leader不做任何响应
         *    1: leader收到消息后，会给producer应答
         */
        configs.put("acks", "1");

        // 将数据发送到kafka
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(configs);

        /**
         * 将消息封装成一个ProducerRecord
         * topic: 消息要送到那个主题
         * parition: 消息要放到哪个主题下的那个分区
         * timestamp: 什么时候发送的时间
         * key: 消息的key
         * value: 消息本身
         * 注意：
         *     消息发送的时候，到底该去往那个分区，是由 partition > key > 轮询
         */
        int i = 0;
        while (true) {
            Thread.sleep(500);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("kafka",UUID.randomUUID().toString(),"this is a message " + i);
            i++;
            kafkaProducer.send(producerRecord);// 0
            System.out.println("i = " + i);
        }


    }


}
