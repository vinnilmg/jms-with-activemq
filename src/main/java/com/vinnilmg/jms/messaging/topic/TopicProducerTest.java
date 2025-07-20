package com.vinnilmg.jms.messaging.topic;

import javax.jms.JMSException;
import javax.naming.NamingException;

import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;
import static com.vinnilmg.jms.messaging.MessagingUtils.createTextMessage;
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
                final var isEbook = false;
                final var message = createTextMessage(session, "Hello World com ebook = " + isEbook);
                message.setBooleanProperty("ebook", isEbook); // Message Selector
                producer.send(message);
            }

            context.close();
        }
    }
}
