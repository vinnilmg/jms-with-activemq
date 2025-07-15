package com.vinnilmg.jms.messaging;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.util.stream.IntStream;

import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createProducer;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;

public class ProducerTest {

    public static void initProducer() throws NamingException, JMSException {
        final var context = createContext();

        try (final var connection = createConnection(context)) {
            connection.start();

            try (
                    final var session = createSession(connection);
                    final var producer = createProducer(context, session)
            ) {
                System.out.println("Publicando mensagem...");

                IntStream.range(0, 100)
                        .forEach(i -> {
                            try {
                                final var message = session.createTextMessage("Hello World - " + i);
                                producer.send(message);
                            } catch (JMSException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }

            context.close();
        }
    }
}
