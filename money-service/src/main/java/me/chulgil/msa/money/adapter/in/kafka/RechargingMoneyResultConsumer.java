package me.chulgil.msa.money.adapter.in.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.chulgil.msa.common.CountDownLatchManager;
import me.chulgil.msa.common.LoggingProducer;
import me.chulgil.msa.common.RechargingMoneyTask;
import me.chulgil.msa.common.SubTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class RechargingMoneyResultConsumer {

    private final KafkaConsumer<String, String> consumer;

    private final LoggingProducer loggingProducer;

    @NotNull
    private final CountDownLatchManager countDownLatchManager;

    public RechargingMoneyResultConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                                         @Value("${task.result.topic}") String topic,
                                         LoggingProducer loggingProducer,
                                         CountDownLatchManager countDownLatchManager) {
        this.loggingProducer = loggingProducer;
        this.countDownLatchManager = countDownLatchManager;
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "my-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        Thread consumerThread = new Thread(() -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("Received message: " + record.key() + " / " + record.value());
                        // record: RechargingMoneyTask, ( all subtask is don)

                        RechargingMoneyTask task;

                        try {
                            task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        List<SubTask> subTasks = task.getSubTasks();
                        boolean taskResult = true;

                        for (SubTask subTask : subTasks) {
                            if (subTask.getStatus().equals("fail")) {
                                taskResult = false;
                                break;
                            }
                        }

                        // 모두 성공인 경우
                        if (taskResult) {
                            this.loggingProducer.sendMessage(task.getTaskId(), "task success");
                            this.countDownLatchManager.setDataForKey(task.getTaskId(), "success");
                        } else {
                            this.loggingProducer.sendMessage(task.getTaskId(), "task failed");
                            this.countDownLatchManager.setDataForKey(task.getTaskId(), "failed");
                        }

                        Thread.sleep(3000); // TODO : Delete when test is done

                        this.countDownLatchManager.getCountDownLatch(task.getTaskId()).countDown();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }
}
