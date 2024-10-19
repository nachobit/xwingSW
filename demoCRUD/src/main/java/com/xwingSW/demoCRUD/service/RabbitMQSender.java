package com.xwingSW.demoCRUD.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwingSW.demoCRUD.config.RabbitMQConfig;

@Service
public class RabbitMQSender {
    private final AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitMQSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage(String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, message);
        System.out.println("Sent message: " + message);
    }
}