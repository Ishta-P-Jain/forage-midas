package com.jpmc.midascore.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConduit {

    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${general.kafka-topic}")
    private String topic;

    public DatabaseConduit(
            UserRepository userRepository,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public void publish(Transaction transaction) {
        try {
            String json = objectMapper.writeValueAsString(transaction);
            kafkaTemplate.send(topic, json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish transaction to Kafka", e);
        }
    }
}
