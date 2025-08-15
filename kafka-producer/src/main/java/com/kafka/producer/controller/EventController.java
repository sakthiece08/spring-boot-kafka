package com.kafka.producer.controller;

import com.kafka.producer.service.KafkaMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class EventController {

    private final KafkaMessagePublisher publisher;

    @GetMapping("publish?message={message}")
    public ResponseEntity<?> publishMessage(@RequestParam String message) {
        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body("Message cannot be null or empty");
        }
        publisher.publishMessage(message);
        return ResponseEntity.ok("Message published successfully");
    }
}
