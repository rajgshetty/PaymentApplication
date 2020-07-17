package com.wellsfargo.payment.app.alert.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.wellsfargo.payment.app.alert.service.KafkaSMSService;

@Component
public class KafkaConsumerPaymentAlert {

	@Autowired
	private KafkaSMSService smsService;

	@KafkaListener(topics = "payment") 
	public void sendSms(String data) {
		smsService.sendSms(data);
	}
}
