package com.fooddeliveryAPIs.food_orders.sqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderProducer {
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${sqs.queue.url}")
    private String queueUrl;

    public OrderProducer(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    public void sendOrderId(Long orderId) {
        try {
            Map<String,Object> payload = new HashMap<>();
            payload.put("orderId", orderId);
            String body = objectMapper.writeValueAsString(payload);

            SendMessageRequest req = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(body)
                    .messageGroupId("orderGroup") // if FIFO required; for standard omit
                    .build();

            sqsClient.sendMessage(req);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
