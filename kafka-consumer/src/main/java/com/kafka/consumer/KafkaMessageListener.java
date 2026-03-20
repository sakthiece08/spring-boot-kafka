package com.kafka.consumer;


import com.kafka.common.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListener {

    Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "teq_topic_1", groupId = "my_group_1")
    public void listen(String message) {
        logger.info("Received message: {}", message);
    }

    @RetryableTopic(attempts = "3")
    @KafkaListener(topics = "user_topic_2", groupId = "user_group_id")
    public void consumeEvents(User user) {
        logger.info("Received message: {}", user.toString());
        if(user.id() == 1)
            throw new RuntimeException("Simulated exception for user with id 1");
    }

    @DltHandler
    public void receivedDLTMessage(User user) {
        logger.info("Received DLT: {}", user.toString());
    }
}
