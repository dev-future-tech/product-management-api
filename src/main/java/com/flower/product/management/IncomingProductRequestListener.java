package com.flower.product.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class IncomingProductRequestListener {

    private final Logger log = LoggerFactory.getLogger(IncomingProductRequestListener.class);

    @Resource
    ProductApprovalService service;


    @RabbitListener(queues = {"product-requests"})
    public void readProductRequest(ProductApprovalMessage message) {
        log.debug("Incoming product request: {}", message);
        this.service.sendProductApproval(message.getProductId());
    }
}
