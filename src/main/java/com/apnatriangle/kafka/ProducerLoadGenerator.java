package com.apnatriangle.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerLoadGenerator {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(ProducerLoadGenerator.class);

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        long start = System.currentTimeMillis();
        long reportingStart = System.currentTimeMillis();
        int reportingInterval = 10000;
        int numRecords = 1000000;

        for (int i = 0; i < numRecords; i++) {
            sendMessage(logger, producer, i);
            if (i % reportingInterval == 0) {
                long currentTime = System.currentTimeMillis();
                long reportingElapsed = currentTime - reportingStart;
                System.out.printf("@ %d. %d records sent in %d ms%n", i, reportingInterval, reportingElapsed);
                reportingStart = currentTime;
            }
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("%d records sent in %d ms", numRecords, elapsed);
        producer.close();
    }

    private static void sendMessage(final Logger logger, KafkaProducer<String, String> producer, int index) {
        final String template = "{ id: %d, name: \"Satish\", city: \"SomeCity\", \"address\": {    \"streetAddress\": \"21 2nd Street\",    \"city\": \"New York\",    \"state\": \"NY\",    \"postalCode\": \"10021-3100\"  },  \"phoneNumbers\": [    {      \"type\": \"home\",      \"number\": \"212 555-1234\"    },    {      \"type\": \"office\",      \"number\": \"646 555-4567\"    },    {      \"type\": \"mobile\",      \"number\": \"123 456-7890\"    }  ],  \"children\": [],  \"spouse\": Beautiful}";
        String key = Integer.toString(index);
        String message = String.format(template, index);
        ProducerRecord<String, String> record = new ProducerRecord<>("topic1", key, message);
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null)
                    logger.error("Error while publishing message", e);
            }
        });
    }

}
