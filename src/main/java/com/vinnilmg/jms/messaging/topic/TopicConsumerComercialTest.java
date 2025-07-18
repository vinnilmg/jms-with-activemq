package com.vinnilmg.jms.messaging.topic;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;
import static com.vinnilmg.jms.messaging.MessagingUtils.createTopicConsumer;

public class TopicConsumerComercialTest implements Runnable {
    private static final String TOPIC_CONSUMER = "comercial";
    private static final String TOPIC_SUBSCRIBER = "comercial-subscriber";

    @Override
    public void run() {
        try {
            initConsumerComercial();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initConsumerComercial() throws NamingException, JMSException {
        final var currentThread = Thread.currentThread();
        final var context = createContext();

        try (final var connection = createConnection(context, TOPIC_CONSUMER)) {
            connection.start();

            try (
                    final var session = createSession(connection);
                    final var consumer = createTopicConsumer(context, session, TOPIC_SUBSCRIBER)
            ) {
                System.out.println(String.format("[COMERCIAL][%s] Conectado...", currentThread.getName()));
                consumer.setMessageListener(message -> {
                    final var textMessage = (TextMessage) message;
                    try {
                        System.out.println("[COMERCIAL] Mensagem recebida: " + textMessage.getText());
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
