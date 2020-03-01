package com.apnatriangle.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleProducerCallBack {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(SimpleProducerCallBack.class);

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        for (int i = 0; i < 10; i++) {
            sendMessage(logger, producer, i);
        }
        producer.close();
    }

    private static void sendMessage(final Logger logger, KafkaProducer<String, String> producer, int index) {
        String key = Integer.toString(index);
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic1", key, "Value " + key);
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null)
                    logger.error("Error while publishing message", e);

                logger.info("Published Message Successfully." +
                        ", Topic: " + recordMetadata.topic() +
                        ", Partition: " + recordMetadata.partition() +
                        ", Offset: " + recordMetadata.offset());
            }
        });
    }

}
