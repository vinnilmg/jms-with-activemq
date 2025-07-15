package com.vinnilmg.jms;

import com.vinnilmg.jms.messaging.ConsumerTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import javax.naming.NamingException;

@SpringBootApplication
public class JmsApplication {

    public static void main(String[] args) throws NamingException, JMSException {
        SpringApplication.run(JmsApplication.class, args);
        ConsumerTest.initConsumer();
    }
}
