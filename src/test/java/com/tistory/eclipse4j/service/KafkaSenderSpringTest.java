package com.tistory.eclipse4j.service;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tistory.eclipse4j.domain.MyTopic;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaSenderSpringTest {

	@Autowired
	private MyTopicKafkaSender sender;
	@Autowired
	private MyTopicKafkaListener listener;
	
	@Test
	public void testSend() throws Exception {
		MyTopic myTopic = new MyTopic();
		myTopic.setKey("TopicKey");
		myTopic.setData("Data Topic");
		sender.send("mytopic", myTopic);
		
		listener.getLatch().await(10000, TimeUnit.MILLISECONDS);
	}

}
