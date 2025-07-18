package com.vinnilmg.jms;

import com.vinnilmg.jms.messaging.queue.QueueConsumerTest;
import com.vinnilmg.jms.messaging.queue.QueueProducerTest;
import com.vinnilmg.jms.messaging.topic.TopicConsumerComercialTest;
import com.vinnilmg.jms.messaging.topic.TopicConsumerEstoqueTest;
import com.vinnilmg.jms.messaging.topic.TopicProducerTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.util.concurrent.Executors;

@SpringBootApplication
public class JmsApplication {

    public static void main(String[] args) throws NamingException, JMSException {
        SpringApplication.run(JmsApplication.class, args);

        // Queue
        QueueProducerTest.initProducer();
        QueueConsumerTest.initConsumer();

        // Topic
        TopicProducerTest.initProducer();

        final var executor = Executors.newFixedThreadPool(3);
        executor.submit(new TopicConsumerEstoqueTest());
        executor.submit(new TopicConsumerComercialTest());
    }
}
