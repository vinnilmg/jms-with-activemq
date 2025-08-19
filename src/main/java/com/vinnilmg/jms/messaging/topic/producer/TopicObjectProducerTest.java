package com.vinnilmg.jms.messaging.topic.producer;

import javax.jms.JMSException;
import javax.naming.NamingException;

import static com.vinnilmg.jms.domain.PedidoFactory.generateRandomOrder;
import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createObjectMessage;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;
import static com.vinnilmg.jms.messaging.MessagingUtils.createTopicProducer;

public class TopicObjectProducerTest {

    public static void initProducer() throws NamingException, JMSException {
        final var context = createContext();

        try (final var connection = createConnection(context)) {
            connection.start();

            try (
                    final var session = createSession(connection);
                    final var producer = createTopicProducer(context, session)
            ) {
                final var order = generateRandomOrder();
                final var message = createObjectMessage(session, order);
                message.setBooleanProperty("isObject", true);
                producer.send(message);
            }

            context.close();
        }
    }
}
