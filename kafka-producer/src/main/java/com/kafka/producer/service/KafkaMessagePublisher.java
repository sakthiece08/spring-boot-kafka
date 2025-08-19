package com.kafka.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    private static final Logger logger = LogManager.getLogger(KafkaMessagePublisher.class);

    public void publishMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("teq_topic_1", message);
        future.whenComplete((result, ex) -> {
            if (ex == null)
                logger.info("Sent message=[{}] with offset=[{}] partition {}",
                        message, result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            else
                logger.error("Unable to send message=[{}] due to {} ", message, ex.getMessage());
        });
    }
}
