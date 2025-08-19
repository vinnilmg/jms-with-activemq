package com.vinnilmg.jms.messaging.topic.consumer;

import com.vinnilmg.jms.domain.Pedido;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;

import static com.vinnilmg.jms.messaging.MessagingUtils.createConnection;
import static com.vinnilmg.jms.messaging.MessagingUtils.createContext;
import static com.vinnilmg.jms.messaging.MessagingUtils.createSession;
import static com.vinnilmg.jms.messaging.MessagingUtils.createTopicConsumerToObjectMessage;

public class TopicConsumerToObjectMessageTest implements Runnable {
    private static final String TOPIC_CONSUMER = "object-message";
    private static final String TOPIC_SUBSCRIBER = "object-message-subscriber";

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
                    final var consumer = createTopicConsumerToObjectMessage(context, session, TOPIC_SUBSCRIBER)
            ) {
                System.out.println(String.format("[OBJECT_MESSAGE][%s] Conectado...", currentThread.getName()));
                consumer.setMessageListener(message -> {
                    final var objectMessage = (ObjectMessage) message;
                    try {
                        final var pedido = (Pedido) objectMessage.getObject();
                        System.out.println("[OBJECT_MESSAGE] Mensagem recebida: " + pedido);
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
