package com.xwingSW.demoCRUD.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.xwingSW.demoCRUD.config.RabbitMQConfig;

@Service
public class RabbitMQReceiver {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}