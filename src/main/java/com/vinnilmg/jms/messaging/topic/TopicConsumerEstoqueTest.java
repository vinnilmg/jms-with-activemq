package com.vinnilmg.jms.messaging.topic;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;
import static com.vinnilmg.jms.messaging.MessagingUtils.createTopicConsumerWithMessageSelector;

public class TopicConsumerEstoqueTest implements Runnable {
    private static final String TOPIC_CONSUMER = "estoque";
    private static final String TOPIC_SUBSCRIBER = "estoque-subscriber";

    @Override
    public void run() {
        try {
            initConsumerEstoque();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConsumerEstoque() throws JMSException, NamingException {
        final var currentThread = Thread.currentThread();
        final var context = createContext();

        try (final var connection = createConnection(context, TOPIC_CONSUMER)) {
            connection.start();

            try (
                    final var session = createSession(connection);
                    final var consumer = createTopicConsumerWithMessageSelector(context, session, TOPIC_SUBSCRIBER)
            ) {
                System.out.println(String.format("[ESTOQUE][%s] Conectado...", currentThread.getName()));
                consumer.setMessageListener(message -> {
                    final var textMessage = (TextMessage) message;
                    try {
                        System.out.println("[ESTOQUE] Mensagem recebida: " + textMessage.getText());
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
