package com.kafka.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessagePublisher {

    private final KafkaTemplate<String, Object> template;

    public void publishMessage(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("topic_name", message);
        future.whenComplete((result, ex) -> {
            if (ex == null)
                log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            else
                log.error("Unable to send message=[{}] due to {} ", message, ex.getMessage());
        });
    }
}
