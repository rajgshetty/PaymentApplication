package com.wellsfargo.payment.app.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.payment.app.alert.service.KafkaSMSService;

/**
 * @author rajkumar
 *
 */
@RestController
public class SMSTestController {
	@Autowired
	private KafkaSMSService smsService;

	@GetMapping("/test")
	private String test() {
		smsService.sendSms("Hello");
		return "Hello";
	}

}
