package com.wellsfargo.payment.app.model;

import java.math.BigDecimal;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model class which have info about fields
 * @author rajkumar
 *
 */
@Document(collection = "Account")
public class Account {
	
	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
	private Long accountId;
	private int accountNumber;
	@Size(min = 3, message = "customer name should have atleast three characters")
	private String customerName;
	private BigDecimal accountBalance;

	public Account() {
		super();
	}

	public Account(Long accountId, int accountNumber, String customerName, BigDecimal accountBalance) {
		super();
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.customerName = customerName;
		this.accountBalance = accountBalance;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountNumber=" + accountNumber + ", customerName=" + customerName
				+ ", accountBalance=" + accountBalance + "]";
	}

}
