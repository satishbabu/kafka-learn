package com.apnatriangle.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ReplyMessages {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(ReplyMessages.class);

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // assign
        TopicPartition partitionToRead = new TopicPartition("topic1", 0);
        long offsetStart = 10L;
        consumer.assign(Arrays.asList(partitionToRead));

        // seek
        consumer.seek(partitionToRead, offsetStart);


        int numberOfMessages = 0;
        final int TOTAL_MESSAGES_TO_READ = 5;
        while (numberOfMessages < TOTAL_MESSAGES_TO_READ) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                logger.info(
                        "key: " + record.key() +
                                ", value: " + record.value() +
                                ", Partition: " + record.partition() +
                                ", Offset: " + record.partition());

                if (++numberOfMessages >= TOTAL_MESSAGES_TO_READ)
                    break;
            }
        }
    }
}
