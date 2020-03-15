package kafka.clients;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;


import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class KafkaSimpleConsumer<K, V> implements Serializable {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String KEY_SERIALIZER = "org.apache.kafka.common.serialization.IntegerSerializer";
    private static final String VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    private final String CLIENT_ID = "KafkaProducer";
    private static final Logger LOGGER = Logger.getLogger(KafkaProducer.class);

    private KafkaConsumer<String, String> consumer;

    public KafkaSimpleConsumer(){
        final Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroup1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        this.setConsumer(new KafkaConsumer<>(props));
    }

    private void setConsumer(KafkaConsumer<String, String> consumer){
        this.consumer = consumer;
    }

    private KafkaConsumer<String, String> getConsumer(){
        return consumer;
    }

    public void subscribe(String topic){
        this.getConsumer().subscribe(Collections.singletonList(topic));
    }

    public ConsumerRecords<String, String> poll(Duration timeout){
        return this.getConsumer().poll(timeout);
    }

    public int getPartitionCount(String topic){
        return this.getConsumer().partitionsFor(topic).size();
    }

    public void commitAsync(){
        this.getConsumer().commitAsync();
    }

    public void close(){
        if (this.getConsumer() != null){
            this.getConsumer().close();
        }
    }
}
