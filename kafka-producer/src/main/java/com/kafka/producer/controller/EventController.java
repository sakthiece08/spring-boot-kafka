package com.kafka.producer.controller;

import com.kafka.producer.service.KafkaMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EventController {

    private KafkaMessagePublisher publisher;

    public EventController(KafkaMessagePublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message) {
        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body("Message cannot be null or empty");
        }
        publisher.publishMessageToTopic(message);
        return ResponseEntity.ok("Message published successfully");
    }
}
