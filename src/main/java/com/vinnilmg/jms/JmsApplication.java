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
    private static final boolean QUEUE_ACTIVE = false;
    private static final boolean TOPIC_ACTIVE = true;

    public static void main(String[] args) throws NamingException, JMSException {
        SpringApplication.run(JmsApplication.class, args);

        if (QUEUE_ACTIVE) {
            QueueProducerTest.initProducer();
            QueueConsumerTest.initConsumer();
        }

        if (TOPIC_ACTIVE) {
            TopicProducerTest.initProducer();
            TopicProducerTest.initProducerWithXML();

            final var executor = Executors.newFixedThreadPool(2);
            executor.submit(new TopicConsumerEstoqueTest());
            executor.submit(new TopicConsumerComercialTest());
        }
    }
}
