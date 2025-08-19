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

    @GetMapping("/publish/bulk")
    public ResponseEntity<?> publishBulkMessage() {
        // for 1000 times call publishMessageToTopic method
        for (int i = 0; i < 1000; i++) {
            String message =  "message " + (i + 1);
            publisher.publishMessageToTopic(message);
        }

        return ResponseEntity.ok("Bulk message published successfully");
    }
}
