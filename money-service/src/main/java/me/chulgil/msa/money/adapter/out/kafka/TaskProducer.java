package me.chulgil.msa.money.adapter.out.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.chulgil.msa.common.RechargingMoneyTask;
import me.chulgil.msa.money.application.port.out.SendRechargingMoneyTaskPort;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {

    private final KafkaProducer<String, String> producer;

    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers") String bootstrapservers,
                        @Value("${task.topic}") String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapservers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void sendRechargingMoneyTask(RechargingMoneyTask task) {
        this.sendMessage(task.getTaskId(), task);
    }

    public void sendMessage(String key, RechargingMoneyTask value) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonValue;
        try {
            jsonValue = mapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ProducerRecord<String, String> record = new ProducerRecord<>(
            topic,
            key,
            jsonValue
        );
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            }
        });
    }
}
