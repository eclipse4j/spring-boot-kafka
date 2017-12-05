package com.tistory.eclipse4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.tistory.eclipse4j.domain.MyTopic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyTopicKafkaSender {

	@Autowired
	private KafkaTemplate<String, MyTopic> topicKafkaTemplate;

	public void send(String topic, MyTopic payload) {
		log.info("Send : Key={}, Topic ==================== > {}", topic, payload);
		topicKafkaTemplate.send(topic, payload);
	}
}
