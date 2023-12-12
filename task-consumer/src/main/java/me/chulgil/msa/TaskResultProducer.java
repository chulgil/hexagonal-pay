package me.chulgil.msa;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskResultProducer {

    private final KafkaProducer<String, String> producer;

    private final String topic;

    public TaskResultProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                              @Value("${task.result.topic}") String topic) {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;

    }

    public void sendTaskResult(String key, Object task) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;
        try {
            jsonStringToProduce = mapper.writeValueAsString(task);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        producer.send(new org.apache.kafka.clients.producer.ProducerRecord<>(topic, key, jsonStringToProduce), (metadata, exception) -> {
            if (exception == null) {
                // System.out.println("Message sent successfully. Offset: " + metadata.offset());
            } else {
                exception.printStackTrace();
                // System.err.println("Failed to send message: " + exception.getMessage());
            }
        });
    }
}
