package com.fooddeliveryAPIs.food_orders.sqs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddeliveryAPIs.food_orders.model.Order;
import com.fooddeliveryAPIs.food_orders.model.OrderStatus;
import com.fooddeliveryAPIs.food_orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
public class OrderConsumer {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    @Value("${sqs.queue.url}")
    private String queueUrl;

    public OrderConsumer(SqsClient sqsClient, ObjectMapper objectMapper, OrderRepository orderRepository) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    @Scheduled(fixedDelayString = "${sqs.poll.delay:5000}")
    public void poll() {
        ReceiveMessageRequest r = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(5)
                .build();
        List<Message> messages = sqsClient.receiveMessage(r).messages();
        for (Message m : messages) {
            try {
                JsonNode node = objectMapper.readTree(m.body());
                Long orderId = node.get("orderId").asLong();
                Order o = orderRepository.findById(orderId).orElse(null);
                if (o != null && o.getStatus() == OrderStatus.PENDING) {
                    o.setStatus(OrderStatus.PROCESSING);
                    orderRepository.save(o);

                    // simulate work you can increase and decrease stimulation time
                    Thread.sleep(1000);

                    o.setStatus(OrderStatus.PROCESSED);
                    orderRepository.save(o);
                }
                // delete message
                sqsClient.deleteMessage(DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(m.receiptHandle())
                        .build());
            } catch (Exception ex) {
                // handle/log; optionally let it become visible again for retries
                ex.printStackTrace();
            }
        }
    }
}
