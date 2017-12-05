package com.tistory.eclipse4j.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.tistory.eclipse4j.domain.MyTopic;

@EnableKafka
@Configuration
public class KafkaConfiguration {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	public ProducerFactory<String, MyTopic> topicProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, MyTopic> topicKafkaTemplate() {
		return new KafkaTemplate<>(topicProducerFactory());
	}

	public ConsumerFactory<String, MyTopic> topicListenerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "mytopic");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
				new JsonDeserializer<>(MyTopic.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, MyTopic> topicKafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, MyTopic> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(topicListenerFactory());
		return factory;
	}
}
