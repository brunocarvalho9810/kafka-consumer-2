package com.kafka.consumer2.config;

import java.util.HashMap;

import com.kafka.consumer2.model.Person;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * Classe responsável pelas configurações do Kafka Consumer
 * @author <a href="https://github.com/brunocarvalho9810/"> Bruno Carvalho </a>
 */
@EnableKafka
@Configuration
public class ConsumerKafkaConfig {

	@Autowired
	private KafkaProperties kafkaProperties; // Configuracoes do Kafka

	/**
	 * Responsável por gerar ConsumerFactory, onde consiste todas configurações do kafka consumer
	 * @return ProducerFactory<String, String>
	 * @author <a href="https://github.com/brunocarvalho9810/"> Bruno Carvalho </a>
	 */
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		var configs = new HashMap<String, Object>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers()); // Pega informacao no application.properties
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Como deseja desserializar as mensagens
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Como deseja desserializar a chave da mensagem
		return new DefaultKafkaConsumerFactory<>(configs);
	}

	/**
	 * Responsável por gerar ConcurrentKafkaListenerContainerFactory, que permite a criação de contêineres de ouvintes
	 * para processar mensagens em tópicos do Apache Kafka.
	 * @return ConcurrentKafkaListenerContainerFactory<String, String>
	 * @author <a href="https://github.com/brunocarvalho9810/"> Bruno Carvalho </a>
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
		var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	/**
	 * Responsável por gerar ConsumerFactory, onde consiste todas configurações do kafka consumer
	 * @return ProducerFactory<String, Person>
	 * @author <a href="https://github.com/brunocarvalho9810/"> Bruno Carvalho </a>
	 */
	@Bean
	public ConsumerFactory<String, Person> personConsumerFactory(){
		var configs = new HashMap<String, Object>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers()); // Pega informacao no application.properties
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Como deseja desserializar as mensagens
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // Como deseja desserializar a chave da mensagem
		var jsonDeserializer = new JsonDeserializer<>(Person.class)
				.trustedPackages("*") // confiar em qualquer pacote
				.forKeys(); // achar a classe em qualquer pacote

		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), jsonDeserializer);
	}

	/**
	 * Responsável por gerar ConcurrentKafkaListenerContainerFactory, que permite a criação de contêineres de ouvintes
	 * para processar mensagens em tópicos do Apache Kafka.
	 * @return ConcurrentKafkaListenerContainerFactory<String, Person>
	 * @author <a href="https://github.com/brunocarvalho9810/"> Bruno Carvalho </a>
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Person> personKafkaListenerContainerFactory(){
		var factory = new ConcurrentKafkaListenerContainerFactory<String, Person>();
		factory.setConsumerFactory(personConsumerFactory());
		return factory;
	}
}
