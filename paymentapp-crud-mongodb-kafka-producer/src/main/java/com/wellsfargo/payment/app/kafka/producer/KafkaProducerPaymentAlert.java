package com.wellsfargo.payment.app.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author rajkumar
 * kafka producer class 
 */
@Component
public class KafkaProducerPaymentAlert {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * payment.alert.topic, have to set in the properties file to read it
	 */
	@Value("${payment.alert.topic}")
	private String kafkaTopic;

	/**
	 * @param message
	 * used to send the message to consumer
	 */
	public void send(String message) {

		kafkaTemplate.send(kafkaTopic, message);
	}

}
