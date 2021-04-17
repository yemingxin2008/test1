
package com.ymx.mq;

import javax.jms.Destination;

import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.QosSettings;
import org.springframework.stereotype.Service;
import javax.jms.DeliveryMode;
import java.util.Random;

/**
 * 
 * @classDesc: 功能描述:(往消息服务 推送 邮件信息)
 * @author: 蚂蚁课堂创始人-余胜军
 * @QQ: 644064779
 * @QQ粉丝群: 116295598
 * @createTime: 2017年10月24日 下午11:40:45
 * @version: v1.0
 * @copyright:每特学院(蚂蚁课堂)上海每特教育科技有限公司
 */
@Service("registerMailboxProducer")
public class RegisterMailboxProducer {
	@Value("${spring.messages.queue}")
	private String message_queue;
	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate ;

	public void sendMess(String mail) {
		Destination activeMQQueue = new ActiveMQQueue(message_queue);
		Random rand = new Random();
		for (int i=0;i<10;i++){
			int id = i;//rand.nextInt(100);
			String json = message(mail,id);

			//没有优先级
			//send(activeMQQueue, json);
			//有优先级
			sendMsg(activeMQQueue, json,id);
		}

	}

	public void sendMsg(Destination destination, String text, int priority) {
		//获取jmsTemplate对象
		JmsTemplate jmsTemplate = jmsMessagingTemplate.getJmsTemplate();
		jmsTemplate.setPriority(priority);
		//创建QosSettings对象
		QosSettings settings = new QosSettings();
		//设置优先级
		settings.setPriority(priority);
		//设置发送模式
		settings.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		//设置延时发送时间
		settings.setTimeToLive(1000L);
		//将设置传入
		jmsTemplate.setQosSettings(settings);
		//发送消息
		jmsMessagingTemplate.convertAndSend(destination, text);
	}



	public void send(Destination destination,String json)
	{
		jmsMessagingTemplate.convertAndSend(destination, json);

	}

	private String message(String mail,int id) {
		JSONObject root = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", "sms_mail");
		JSONObject content = new JSONObject();
		content.put("mail", mail);
		root.put("header", header);
		root.put("content", content);

		root.put("id",id);
		return root.toJSONString();

	}



}
