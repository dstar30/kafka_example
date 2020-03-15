package kafka.clients;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.Serializable;
import java.util.Properties;

public class KafkaSimpleProducer<K, V> implements Serializable {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String KEY_SERIALIZER = "org.apache.kafka.common.serialization.IntegerSerializer";
    private static final String VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    private final String CLIENT_ID = "KafkaProducer";
    private static final Logger LOGGER = Logger.getLogger(KafkaProducer.class);

    private KafkaProducer<String, String> producer;

    public KafkaSimpleProducer(){
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KEY_SERIALIZER);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER);
        this.setProduer(new KafkaProducer<>(props));
    }

    private void setProduer(KafkaProducer<String, String> producer){
        this.producer = producer;
    }

    private KafkaProducer<String, String> getProducer(){
        return producer;
    }

    /**
     * Sends message to kafka topic
     */
    public void send(String topic, String message){
        this.getProducer().send(new ProducerRecord<>(topic, message));
    }

    public void close(){
        if (this.getProducer() != null){
            this.getProducer().close();
        }
    }
}