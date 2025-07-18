package com.vinnilmg.jms.messaging.topic;

import javax.jms.JMSException;
import javax.naming.NamingException;

import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;
import static com.vinnilmg.jms.messaging.MessagingUtils.createTopicProducer;

public class TopicProducerTest {

    public static void initProducer() throws NamingException, JMSException {
        final var context = createContext();

        try (final var connection = createConnection(context)) {
            connection.start();

            try (
                    final var session = createSession(connection);
                    final var producer = createTopicProducer(context, session)
            ) {
                System.out.println("Publicando mensagem...");
                final var message = session.createTextMessage("Hello World!");
                producer.send(message);
            }

            context.close();
        }
    }
}
