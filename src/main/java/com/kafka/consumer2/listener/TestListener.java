package com.kafka.consumer2.listener;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por receber mensagens do topico kafka
 * @author <a href="https://github.com/brunocarvalho9810/"> Bruno Carvalho </a>
 */
@Slf4j
@Component
public class TestListener {
	
	private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

	/**
	 * Responsável por receber mensagens do topico "topic-1" e exibir as mensagens por meio de logs
	 * @author <a href="https://github.com/brunocarvalho9810/"> Bruno Carvalho </a>
	 */
	@KafkaListener(topics = "topic-1", groupId = "group-2")
	public void listen(String message,
					   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
					   @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
					   ConsumerRecordMetadata metadata) { // ConsumerRecordMetadata utilizado apartir da versao 2.5
		logger.info("Thread: {} Messages: {} Topico: {} Particao: {} Particao Metadata: {} Offset: {} ",
				Thread.currentThread().getId(),
				message,
				topic,
				partition,
				metadata.partition(),
				metadata.offset());
	}
}