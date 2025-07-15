package com.vinnilmg.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@SpringBootApplication
public class JmsApplication {

    public static void main(String[] args) throws NamingException, JMSException {
        SpringApplication.run(JmsApplication.class, args);
        initConsumer();
    }

    private static void initConsumer() throws NamingException, JMSException {
        final var context = new InitialContext();
        final var factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        try (var connection = factory.createConnection()) {
            connection.start();

            try (var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                final var queue = (Destination) context.lookup("financeiro");
                final var consumer = session.createConsumer(queue);

                consumer.setMessageListener(message -> {
                    final var textMessage = (TextMessage) message;
                    try {
                        System.out.println("Mensagem recebida: " + textMessage.getText());
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                });

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
