package com.tistory.eclipse4j.service;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.tistory.eclipse4j.domain.MyTopic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyTopicKafkaListener {

	private CountDownLatch latch = new CountDownLatch(1);

	public CountDownLatch getLatch() {
		return latch;
	}

	@KafkaListener(topics = "mytopic", containerFactory = "topicKafkaListenerFactory")
	public void reciver(MyTopic topic) {
		log.info("MyTopic => {}", topic);
	}
}
