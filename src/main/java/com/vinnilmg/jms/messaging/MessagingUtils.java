package com.vinnilmg.jms.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessagingUtils {

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
            return (Destination) context.lookup("financeiro");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection createConnection(final InitialContext context) {
        try {
            final var factory = createFactory(context);
            return factory.createConnection();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session createSession(final Connection connection) {
        try {
            return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageConsumer createConsumer(final InitialContext context, final Session session) {
        try {
            final var queue = createQueueDestination(context);
            return session.createConsumer(queue);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageProducer createProducer(final InitialContext context, final Session session) {
        try {
            final var queue = createQueueDestination(context);
            return session.createProducer(queue);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
