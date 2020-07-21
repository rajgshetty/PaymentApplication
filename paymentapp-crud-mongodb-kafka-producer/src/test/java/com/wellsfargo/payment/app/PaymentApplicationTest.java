package com.wellsfargo.payment.app;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.wellsfargo.payment.app.controller.AccountController;
import com.wellsfargo.payment.app.dao.AccountRepository;
import com.wellsfargo.payment.app.dbsequenceid.DatabaseSequence;
import com.wellsfargo.payment.app.model.Account;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import com.wellsfargo.payment.app.service.AccountService;

/**
 * @author rajkumar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class PaymentApplicationTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

	@Test
	void contextLoads() {
	}

	@Autowired
	private AccountService accountService;

	@InjectMocks
	private AccountController userController;

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private DatabaseSequence databaseSequence;

	/**
	 * creating of account
	 */
	@Test
	public void createAccountTest() {
		BigDecimal balance = new BigDecimal("15000");
		// Long id = databaseSequence.getSeq();
		// OR
		Long id = 12345L;
		Account account = new Account(id, 981616, "Raj", balance);
		when(accountRepository.save(account)).thenReturn(account);
		assertEquals("Account is created With Account Id :" + account.getAccountId(),
				accountService.createAccount(account));
	}

	/**
	 * get accounts test case
	 */
	@Test
	public void getAccountsTest() {
		BigDecimal balance = new BigDecimal("20000");
		Long id = databaseSequence.getSeq();
		when(accountRepository.findAll()).thenReturn(
				Stream.of(new Account(300L, 12345, "John", balance), new Account(id, 12345, "Jack", balance))
						.collect(Collectors.toList()));
		assertEquals(2, accountService.getAccounts().size());

	}

	/**
	 * accountNumber testing
	 */
	@Test
	public void getAccountByAccountNumberTest() {
		BigDecimal balance = new BigDecimal("25000");
		Long id = databaseSequence.getSeq();
		int accountNumber = 12345;
		Account account = new Account(id, accountNumber, "Jack", balance);
		when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
		assertEquals(true, accountService.getAccountByAccountNumber(accountNumber).isPresent());
	}

	/**
	 * customerName testing
	 */
	@Test
	public void getAccountByCustomerNameTest() {
		BigDecimal balance = new BigDecimal("25000");
		Long id = databaseSequence.getSeq();
		String customerName = "Sree";
		int accountNumber = 12345;
		Account account = new Account(id, accountNumber, "Sree", balance);
		when(accountRepository.findByCustomerName("Sree")).thenReturn(Optional.of(account));
		assertEquals(true, accountService.getAccountByCustomerName(customerName).isPresent());
	}

	/**
	 * delete method testing
	 */
	@Test
	public void deleteAccountTest() {
		BigDecimal balance = new BigDecimal("15000");
		Long id = databaseSequence.getSeq();
		long accountId = 500L;
		Account account = new Account(id, 12345, "Sam", balance);
		when(accountRepository.findById(500L)).thenReturn(Optional.of(account));
		assertEquals("Account with Id :" + accountId + " is Deleted", accountService.deleteAccount(accountId));
	}

	/**
	 * withdraw account testing
	 */
	@Test
	public void withdrawAccountTest() {
		AccountService mock = Mockito.mock(AccountService.class);
		Long id = databaseSequence.getSeq();
		BigDecimal balance = new BigDecimal("30000");
		Account account = new Account(id, 12345, "Sree", balance);
		Mockito.doReturn("Account with No :" + account.getAccountNumber() + " is withdrawn with " + balance).when(mock)
				.withdraw(account, balance);
	}

	/**
	 * depost test cases
	 */
	@Test
	public void depositAccountTest() {
		Long id = databaseSequence.getSeq();
		AccountService mock = Mockito.mock(AccountService.class);
		BigDecimal balance = new BigDecimal("15000");
		Account account = new Account(id, 12345, "Jack", balance);
		Mockito.doReturn("Account with No :" + account.getAccountNumber() + " is deposited with " + balance).when(mock)
				.deposit(account, balance);
	}
}
