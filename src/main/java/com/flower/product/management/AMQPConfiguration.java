package com.flower.product.management;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"!test"})
public class AMQPConfiguration {

    static final String topicExchangeName = "product-exchange";

    static final String queueName = "product-approvals";

    @Bean
    Queue queue() {
        return new Queue(queueName, true, false, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName, true, false);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("product.approvals.#");
    }

}
