package com.wellsfargo.payment.app.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.payment.app.model.Account;

/**
 * @author rajkumar
 *
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, Long> {

	Optional<Account> findByAccountNumber(int accountNumber);

	Optional<Account> findByCustomerName(String customerName);

}
