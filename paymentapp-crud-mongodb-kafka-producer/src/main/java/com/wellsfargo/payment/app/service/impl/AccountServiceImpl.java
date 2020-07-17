package com.wellsfargo.payment.app.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.payment.app.dao.AccountRepository;
import com.wellsfargo.payment.app.exception.AccountNotFoundException;
import com.wellsfargo.payment.app.model.Account;
import com.wellsfargo.payment.app.service.AccountService;

/**
 * @author rajkumar
 * Implenting the methods of service class
 * Performing the crud operations.
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<Account> getAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Optional<Account> getAccount(Long accountId) {
		return accountRepository.findById(accountId);
	}

	@Override
	public Optional<Account> getAccountByAccountNumber(int accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}

	@Override
	public String deposit(Account account, BigDecimal amount) {
		account.setAccountBalance(account.getAccountBalance().add(amount));
		accountRepository.save(account);
		return "Account with No :" + account.getAccountNumber() + " is deposited with " + amount;
	}

	@Override
	public String withdraw(Account account, BigDecimal amount) {
		account.setAccountBalance(account.getAccountBalance().subtract(amount));
		accountRepository.save(account);
		return "Account with No :" + account.getAccountNumber() + " is withdrawn with " + amount;
	}

	@Override
	public String deleteAccount(Long accountId) {
		Optional<Account> findById = accountRepository.findById(accountId);
		if (findById.isPresent()) {
			accountRepository.deleteById(accountId);
			return "Account with Id :" + accountId + " is Deleted";
		} else {
			throw new AccountNotFoundException("Account with Account ID : " + accountId + " not found to delete!");
		}
	}

	@Override
	public String createAccount(Account account) {
		Optional<Account> accountOptional = accountRepository.findByAccountNumber(account.getAccountNumber());
		if (accountOptional.isPresent()) {
			return "Account with Account No :" + account.getAccountNumber() + " already exist!";
		}
		Account savedAccount = accountRepository.save(account);
		return "Account is created With Account Id :" + savedAccount.getAccountId();
	}

	@Override
	public Optional<Account> getAccountByCustomerName(String customerName) {
		return accountRepository.findByCustomerName(customerName);
	}

}
