package com.vinnilmg.jms.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

public class MessagingUtils {
    private static final String QUEUE_NAME = "financeiro";
    private static final String TOPIC_NAME = "loja";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "senha";

    public static InitialContext createContext() {
        try {
            return new InitialContext();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionFactory createFactory(final InitialContext context) {
        try {
            return (ConnectionFactory) context.lookup("ConnectionFactory");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Destination createQueueDestination(final InitialContext context) {
        try {
            return (Destination) context.lookup(QUEUE_NAME);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Topic createTopicDestination(final InitialContext context) {
        try {
            return (Topic) context.lookup(TOPIC_NAME);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection createConnection(final InitialContext context) {
        try {
            final var factory = createFactory(context);
            return factory.createConnection(USERNAME, PASSWORD);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection createConnection(
            final InitialContext context,
            final String consumerName
    ) {
        try {
            final var connection = createConnection(context);
            connection.setClientID(consumerName);
            return connection;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session createSession(
            final Connection connection,
            final boolean transacted,
            final int ackMode
    ) {
        try {
            return connection.createSession(transacted, ackMode);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session createSession(final Connection connection) {
        return createSession(connection, false, Session.AUTO_ACKNOWLEDGE);
    }

    public static Session createSessionWithClientAck(final Connection connection) {
        return createSession(connection, false, Session.CLIENT_ACKNOWLEDGE);
    }

    public static Session createSessionWithTransaction(final Connection connection) {
        return createSession(connection, true, Session.SESSION_TRANSACTED);
    }

    public static MessageConsumer createQueueConsumer(final InitialContext context, final Session session) {
        try {
            final var queue = createQueueDestination(context);
            return session.createConsumer(queue);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageConsumer createTopicConsumer(
            final InitialContext context,
            final Session session,
            final String subscriberName
    ) {
        try {
            final var topic = createTopicDestination(context);
            return session.createDurableSubscriber(topic, subscriberName);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageConsumer createTopicConsumerWithMessageSelector(
            final InitialContext context,
            final Session session,
            final String subscriberName
    ) {
        try {
            final var topic = createTopicDestination(context);
            return session.createDurableSubscriber(
                    topic,
                    subscriberName,
                    "isObject is null OR isObject=false AND ebook is null OR ebook=false", // Exemplo de message selector
                    false
            );
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageConsumer createTopicConsumerToObjectMessage(
            final InitialContext context,
            final Session session,
            final String subscriberName
    ) {
        try {
            final var topic = createTopicDestination(context);
            return session.createDurableSubscriber(
                    topic,
                    subscriberName,
                    "isObject=true", // Exemplo de message selector
                    false
            );
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageProducer createQueueProducer(final InitialContext context, final Session session) {
        try {
            final var queue = createQueueDestination(context);
            return session.createProducer(queue);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageProducer createTopicProducer(final InitialContext context, final Session session) {
        try {
            final var topic = createTopicDestination(context);
            return session.createProducer(topic);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static TextMessage createTextMessage(final Session session, final String message) {
        try {
            System.out.println("Gerando mensagem...");
            return session.createTextMessage(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMessage createObjectMessage(final Session session, final Serializable message) {
        try {
            System.out.println("Gerando mensagem de objeto...");
            return session.createObjectMessage(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
