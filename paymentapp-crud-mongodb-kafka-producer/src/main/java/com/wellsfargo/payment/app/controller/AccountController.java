package com.wellsfargo.payment.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.payment.app.exception.AccountNotFoundException;
import com.wellsfargo.payment.app.kafka.producer.KafkaProducerPaymentAlert;
import com.wellsfargo.payment.app.model.Account;
import com.wellsfargo.payment.app.service.AccountService;
import com.wellsfargo.payment.app.service.SequenceGeneratorService;
/**
 * @author rajkumar
 *
 */
@RestController
@RequestMapping("/account-management")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private KafkaProducerPaymentAlert kafkaProducerPaymentAlert;

	/**
	 * @return
	 * This has been added as part testing purpose ].
	 */
	@GetMapping("/test")
	private String test() {
		return "Hello";
	}

	/**
	 * @return all the account details 
	 */
	@GetMapping("/getAllAccount")
	public List<Account> getAllAccount() {
		return accountService.getAccounts();
	}

	
	/**
	 * @param accountId
	 * @return
	 * based on the accountId provided, will fetch the details
	 */
	@GetMapping("/getAccountById/{accountId}")
	public Account getAccount(@PathVariable Long accountId) {
		Optional<Account> account = accountService.getAccount(accountId);
		return account.orElseThrow(
				() -> new AccountNotFoundException("Account with Account Id : " + accountId + " doesn't exist"));
	}

	/**
	 * @param accountNumber
	 * @return the details based on the account number provided
	 *  
	 */
	@GetMapping("/getAccountByNo/{accountNumber}")
	public Account getAccountByAccountNumber(@PathVariable int accountNumber) {
		Optional<Account> account = accountService.getAccountByAccountNumber(accountNumber);
		return account.orElseThrow(
				() -> new AccountNotFoundException("Account with Account No : " + accountNumber + " doesn't exist"));

	}

	/**
	 * @param customerName
	 * @return the info based on customer name provided
	 * 
	 */
	@GetMapping("/getAccountBycustomerName/{customerName}")
	public Account getAccountByAccountNumber(@PathVariable String customerName) {
		Optional<Account> account = accountService.getAccountByCustomerName(customerName);
		return account.orElseThrow(
				() -> new AccountNotFoundException("Account with Customer Name : " + customerName + " doesn't exist"));

	}

	/**
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	@PutMapping("/depositAccount/{accountNumber}/amount/{amount}")
	public String depositAccount(@PathVariable int accountNumber, @PathVariable BigDecimal amount) {
		Account account = getAccountByAccountNumber(accountNumber);
		String depositMessage = accountService.deposit(account, amount);
		kafkaProducerPaymentAlert.send(depositMessage);
		return depositMessage;
	}

	/**
	 * @param accountNumber
	 * @param amount
	 * @return
	 */
	@PutMapping("/withdrawAccount/{accountNumber}/amount/{amount}")
	public String withdrawAccount(@PathVariable int accountNumber, @PathVariable BigDecimal amount) {
		Account account = getAccountByAccountNumber(accountNumber);
		String withdrawMessage = accountService.withdraw(account, amount);
		kafkaProducerPaymentAlert.send(withdrawMessage);
		return withdrawMessage;
	}

	/**
	 * @param accountId
	 * @return
	 */
	@DeleteMapping("/deleteAccountById/{accountId}")
	public String deleteAccountById(@PathVariable Long accountId) {
		return accountService.deleteAccount(accountId);
	}

	/**
	 * @param account
	 * @return
	 */
	@PostMapping("/createAccount")
	public String createAccount(@Valid @RequestBody Account account) {
		account.setAccountId(sequenceGeneratorService.generateSequence(Account.SEQUENCE_NAME));
		return accountService.createAccount(account);
	}
}
