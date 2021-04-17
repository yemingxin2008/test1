package com.ymx.mq;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class ConsumerDistribute2 {
    private Logger log  = LoggerFactory.getLogger(getClass());
    private static int num=0;
    @JmsListener(destination = "dx_queue")
    public void dequeue(String message) {

        System.out.println("默认工厂："+message);
    }


   // @JmsListener(destination = "ActiveMQ.DLQ", containerFactory = "queueListener")
    public void receiveQueueTest3(ActiveMQMessage message, Session session) throws JMSException,
            JsonProcessingException {
        String text = null;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            text = textMessage.getText();
            log.info("处理死信队列--接收到消息：{}", text);
            sleep(5000);

            message.acknowledge();
        }
    }
    /**
     * queue-test普通队列：消费者1
     */
   // @JmsListener(destination = "dx_queue" )
    public void receiveQueueTest1(ActiveMQMessage message, Session session) throws JMSException,
            JsonProcessingException {
        log.info("receiveQueueTest:1");
        String text = null;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            text = textMessage.getText();
            log.info("queue1接收到消息：{}", text);
            ObjectMapper objectMapper = new ObjectMapper();

            // 手动确认
            message.acknowledge();
        }
    }

    /**
     * queue-test普通队列：消费者2
     */
    //@JmsListener(destination = "dx_queue", containerFactory = "queueListener")
    public void receiveQueueTest2(ActiveMQMessage message, Session session) throws JMSException,
            JsonProcessingException {
        log.info("receiveQueueTest:2");
        String text = null;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            text = textMessage.getText();
            JSONObject content = (JSONObject) JSONObject.parse(text);
            int id= (int) content.get("id");
            log.info(id+"=queue2接收到消息：{}", text);
            // 手动确认
            try{
                if(id%2 == 0){
                    throw new JMSException("JMSException 异常");
                }
                message.acknowledge();
            }catch (JMSException ex){
                ++ num ;
                log.error("=处理次数="+num);
                session.recover();
            }

        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
