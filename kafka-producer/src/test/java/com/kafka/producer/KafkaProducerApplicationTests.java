package com.kafka.producer;

import com.kafka.common.dto.User;
import com.kafka.producer.service.KafkaMessagePublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class KafkaProducerApplicationTests {

	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

	@DynamicPropertySource
    public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}


	@Autowired
    private KafkaMessagePublisher publisher;

	@Test
	public void publishMessageToTopic() {
		publisher.publishMessageToTopic(new User(1, "Tom", "test@test.com"));
		// wait for 10 seconds to let the message be published
		await().pollInterval(Duration.ofSeconds(3))
				.atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
					// assert statement
				});

	}

}
