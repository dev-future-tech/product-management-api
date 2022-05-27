package com.flower.product.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class IncomingProductRequestListener {

    private final Logger log = LoggerFactory.getLogger(IncomingProductRequestListener.class);

    @RabbitListener(queues = {"product-requests"})
    public void readProductRequest(String message) {
        log.debug("Incoming product request: {}", message);
    }
}
