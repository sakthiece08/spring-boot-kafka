package com.kafka.consumer;


import com.kafka.common.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListener {

    Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "teq_topic_1", groupId = "my_group_id_1")
    public void listen(String message) {
        logger.info("Received message: {}", message);
    }

    @KafkaListener(topics = "user_topic_2", groupId = "user_group_id")
    public void consumeEvents(User user) {
        logger.info("Received message: {}", user.toString());
    }
}
