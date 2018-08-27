package com.yyt.secondkill.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String SECOND_KILL_QUEUE = "second_kill.queue";

    /**
     * Direct模式 交换机Exchange
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(SECOND_KILL_QUEUE, true);
    }
}
