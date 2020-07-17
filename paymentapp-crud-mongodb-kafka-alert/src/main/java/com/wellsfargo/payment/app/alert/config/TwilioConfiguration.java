package com.wellsfargo.payment.app.alert.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;

/**
 * @author rajkumar
 * 
 *Twilio configuration file
 *sid, token it will read from application.properties f]ile
 */
@Component
public class TwilioConfiguration {

	@Value("${twilio.account.sid}")
	private String accountSid;
	@Value("${twilio.auth.token}")
	private String authToken;

	public TwilioConfiguration() {
	}

	@PostConstruct
	public void postConstruct() {
		Twilio.init(accountSid, authToken);

	}

	public String getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
