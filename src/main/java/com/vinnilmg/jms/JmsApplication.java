package com.vinnilmg.jms;

import com.vinnilmg.jms.messaging.queue.consumer.QueueConsumerWithTransactedSessionTest;
import com.vinnilmg.jms.messaging.queue.producer.QueueProducerTest;
import com.vinnilmg.jms.messaging.topic.consumer.TopicConsumerEstoqueTest;
import com.vinnilmg.jms.messaging.topic.consumer.TopicConsumerToObjectMessageTest;
import com.vinnilmg.jms.messaging.topic.producer.TopicObjectProducerTest;
import com.vinnilmg.jms.messaging.topic.producer.TopicTextProducerTest;
import com.vinnilmg.jms.messaging.topic.producer.TopicXmlProducerTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.util.concurrent.Executors;

@SpringBootApplication
public class JmsApplication {
    private static final boolean QUEUE_ACTIVE = true;
    private static final boolean TOPIC_ACTIVE = false;

    public static void main(String[] args) throws NamingException, JMSException {
        SpringApplication.run(JmsApplication.class, args);

        if (QUEUE_ACTIVE) {
            QueueProducerTest.initProducer();
            //QueueConsumerTest.initConsumer();
            //QueueConsumerWithManualAckTest.initConsumer();
            QueueConsumerWithTransactedSessionTest.initConsumer();
        }

        if (TOPIC_ACTIVE) {
            TopicTextProducerTest.initProducer();
            TopicXmlProducerTest.initProducer();
            TopicObjectProducerTest.initProducer();

            final var executor = Executors.newFixedThreadPool(2);
            //executor.submit(new TopicConsumerComercialTest());
            executor.submit(new TopicConsumerEstoqueTest());
            executor.submit(new TopicConsumerToObjectMessageTest());
        }
    }
}
