package com.wellsfargo.payment.app.alert.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * @author rajkumar
 *
 */
@Service
public class KafkaSMSService {

	@Value("${twilio.trial.number}") // 
	private String trialNumber;

	public void sendSms(String data) {
		Message.creator(new PhoneNumber("+919035480777"), new PhoneNumber(trialNumber), data).create();
	}

}
