package app;

import kafka.clients.KafkaSimpleConsumer;
import kafka.clients.KafkaSimpleProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class KafkaExmple {
    public static void main(String args[]) {
        String topic = "sample-topic";

        // properties for producer and consumer
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // create producer and consumer
        KafkaSimpleProducer<String, String> producer = new KafkaSimpleProducer<>();
        KafkaSimpleConsumer<String, String> consumer = new KafkaSimpleConsumer<>();

        // send messages to the sample topic
        for(int i = 0; i < 10; i++) {
            producer.send(topic, "Message produced by Kafka Producer" + i);
        }

        // see how many partitions are in the sample topic
        int partitions = consumer.getPartitionCount(topic);
        System.out.println("Number of partitions in " + topic + ": " + partitions);
        // read messages from the sample topic
        consumer.subscribe(topic);
        ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(500));

        // print messages
        System.out.println("Number of records: " + consumerRecords.count());

        for (ConsumerRecord record : consumerRecords) {
            System.out.println("========================");
            System.out.println("Record Key: " + record.key());
            System.out.println("Record value: " + record.value());
            System.out.println("Record partition: " + record.partition());
            System.out.println("Record offset: " + record.offset());
            System.out.println("========================");
        }


        consumer.commitAsync(); // commits the offset of record to broker
        //close producer
        producer.close();
        consumer.close();
    }
}
