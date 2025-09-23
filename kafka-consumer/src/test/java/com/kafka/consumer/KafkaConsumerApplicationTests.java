package com.kafka.consumer;

import com.kafka.common.dto.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:/application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = {
		"spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer",
		"spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer",
		"spring.kafka.consumer.group-id=user_group_id"
})
class KafkaConsumerApplicationTests {
	Logger logger = LoggerFactory.getLogger(KafkaConsumerApplicationTests.class);

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	}


	@Test
    public void testConsumer() {
		kafkaTemplate.send("user_topic_2", new User(1, "test_name", "test_email"));
		logger.info("Message sent to topic");
		// wait for 10 seconds to let the message be published
		await().pollInterval(Duration.ofSeconds(3))
				.atMost(20, TimeUnit.SECONDS).untilAsserted(() -> {
					// assert statement
				});
	}

}
