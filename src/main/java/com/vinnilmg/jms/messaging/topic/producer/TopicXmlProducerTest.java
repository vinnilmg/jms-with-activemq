package com.vinnilmg.jms.messaging.topic.producer;

import javax.jms.JMSException;
import javax.naming.NamingException;

import static com.vinnilmg.jms.domain.PedidoFactory.generateRandomOrder;
import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;
import static com.vinnilmg.jms.messaging.MessagingUtils.createTextMessage;
import static com.vinnilmg.jms.messaging.MessagingUtils.createTopicProducer;
import static com.vinnilmg.jms.utils.XmlUtils.convertToXml;

public class TopicXmlProducerTest {

    public static void initProducer() throws NamingException, JMSException {
        final var context = createContext();

        try (final var connection = createConnection(context)) {
            connection.start();

            try (
                    final var session = createSession(connection);
                    final var producer = createTopicProducer(context, session)
            ) {
                final var order = generateRandomOrder();
                final var xml = convertToXml(order);
                final var message = createTextMessage(session, xml);
                producer.send(message);
            }

            context.close();
        }
    }
}
