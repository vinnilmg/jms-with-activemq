package com.vinnilmg.jms.messaging.queue.consumer;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createQueueConsumer;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSessionWithTransaction;

public class QueueConsumerWithTransactedSessionTest {

    public static void initConsumer() throws NamingException, JMSException {
        final var context = createContext();

        try (final var connection = createConnection(context)) {
            connection.start();

            try (
                    final var session = createSessionWithTransaction(connection);
                    final var consumer = createQueueConsumer(context, session)
            ) {
                System.out.println("Conectado...");
                consumer.setMessageListener(message -> {
                    final var textMessage = (TextMessage) message;
                    try {
                        System.out.println("Mensagem recebida na QUEUE: " + textMessage.getText());
                        session.commit();
                    } catch (JMSException e) {
                        try {
                            session.rollback();
                        } catch (JMSException ex) {
                            throw new RuntimeException(ex);
                        }

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
