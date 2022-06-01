package com.flower.product.management;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"!test"})
public class AMQPConfiguration {

    static final String topicExchangeName = "product-exchange";

    static final String queueName = "product-requests-validated";

    @Bean
    Queue queue() {
        return new Queue(queueName, true, false, false);
    }

    @Bean
    Queue prodRequestsQueue() {
        return new Queue("product-requests");
    }

    @Bean
    Binding prodRequestBinding(@Qualifier("prodRequestsQueue") Queue prodRequestQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(prodRequestQueue)
                .to(exchange)
                .with("product.requests.#");
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
                .with("product.validated.#");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
