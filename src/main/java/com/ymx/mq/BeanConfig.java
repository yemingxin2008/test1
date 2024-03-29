package com.ymx.mq;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class BeanConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean(name = "queue")
    public Queue queue() {
        return new ActiveMQQueue("queue-test");
    }

    @Bean(name = "delayQueue")
    public Queue delayQueue() {
        return new ActiveMQQueue("delay-queue-test");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("topic-test");
    }

    @Bean
    public ActiveMQConnectionFactory   connectionFactory() {

        ActiveMQConnectionFactory   connectionFactory  = new ActiveMQConnectionFactory(username, password, brokerUrl);
        RedeliveryPolicy ry = new RedeliveryPolicy();
        ry.setUseExponentialBackOff(true);
        ry.setMaximumRedeliveries(3);

        ry.setMaximumRedeliveryDelay(2);
        connectionFactory.setRedeliveryPolicy(ry);
        connectionFactory.setDispatchAsync(true);
        return connectionFactory;


    }

    @Bean
    public JmsMessagingTemplate jmsMessageTemplate() {
        return new JmsMessagingTemplate(connectionFactory());
    }

    // 在Queue模式中，对消息的监听需要对containerFactory进行配置
    @Bean("queueListener")
    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        // 关闭事务
        factory.setSessionTransacted(false);
        // 设置手动确认，默认配置中Session是开启了事物的，即使我们设置了手动Ack也是无效的
        factory.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    //在Topic模式中，对消息的监听需要对containerFactory进行配置
    @Bean("topicListener")
    public JmsListenerContainerFactory<?> topicJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
}
