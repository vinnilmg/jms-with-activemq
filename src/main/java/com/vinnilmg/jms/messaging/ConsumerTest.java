package com.vinnilmg.jms.messaging;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createConsumer;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;

public class ConsumerTest {

    public static void initConsumer() throws NamingException, JMSException {
        final var context = createContext();

        try (final var connection = createConnection(context)) {
            connection.start();

            try (
                    final var session = createSession(connection);
                    final var consumer = createConsumer(context, session)
            ) {
                consumer.setMessageListener(message -> {
                    final var textMessage = (TextMessage) message;
                    try {
                        System.out.println("Mensagem recebida: " + textMessage.getText());
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Para nao encerrar a conexao
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            context.close();
        }
    }
}
