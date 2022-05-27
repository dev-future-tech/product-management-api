package com.flower.product.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductApprovalService {

    private final Logger log = LoggerFactory.getLogger(ProductApprovalService.class);

    @Resource
    ObjectMapper objectMapper;

    @Resource
    RabbitTemplate template;

    public void sendProductApproval(Long approvalId) {
        try {
            ApprovedProductMessage messageResponse = new ApprovedProductMessage();
            messageResponse.setProductId(approvalId);
            messageResponse.setApproved(true);

            String response = objectMapper.writeValueAsString(messageResponse);
            log.debug("Sending approval...");
            Message message = MessageBuilder
                    .withBody(response.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON).build();

            template.convertAndSend("product-exchange", "product.approvals.approved", message);
        } catch(Exception e) {
            log.error("Error sending approval", e);
        }
    }
}
