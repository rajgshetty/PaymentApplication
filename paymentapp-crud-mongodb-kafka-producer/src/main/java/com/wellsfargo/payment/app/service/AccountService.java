package com.wellsfargo.payment.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.wellsfargo.payment.app.model.Account;

/**
 * @author rajkumar
 *
 */
public interface AccountService {

	List<Account> getAccounts();

	Optional<Account> getAccount(Long accountId);

	Optional<Account> getAccountByAccountNumber(int accountNumber);

	String deposit(Account account, BigDecimal amount);

	String withdraw(Account account, BigDecimal amount);

	String deleteAccount(Long accountId);

	String createAccount(Account account);

	Optional<Account> getAccountByCustomerName(String customerName);

}
