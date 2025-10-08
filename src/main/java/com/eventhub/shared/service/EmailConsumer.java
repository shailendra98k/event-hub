package com.eventhub.shared.service;

import com.eventhub.shared.dto.EmailMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    private final EmailService emailService;

    @Autowired
    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${rabbitmq.email.queue}")
    public void receiveEmail(EmailMessage message) {
        emailService.sendEmail(message.getTo(), message.getSubject(), message.getBody());
    }
}
