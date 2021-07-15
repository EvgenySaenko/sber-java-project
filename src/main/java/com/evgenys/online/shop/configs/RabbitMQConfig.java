package com.evgenys.online.shop.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Value("${onlineshop.rabbitmq.exchange}")
    private String EXCHANGE;

    @Value("${onlineshop.rabbitmq.routingkeyorder}")
    private String ROUTING_KEY_ORDER;

    @Value("${onlineshop.rabbitmq.routingkeystatus}")
    private String ROUTING_KEY_STATUS;

    @Value("${onlineshop.rabbitmq.queue}")
    private String QUEUE;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Queue queue() {//durable- хранится в очереди пока его кто-нибудь не вычитает
        return new Queue(QUEUE, true, false, false);
    }

    @Bean
    Binding binding() {//связываем exchange c queue с помощью ключа(ROUTING_KEY)
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY_ORDER);
    }

    @Bean
    Binding binding2() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY_STATUS);
    }
}