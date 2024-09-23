package com.test.repository;

import com.test.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {
    AccountRepository accountRepository;
    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        accountRepository = new AccountRepository(entityManager.getEntityManager());
    }

    @Test
    void givenValidData_whenTransferMoney_thenSuccess() {
        accountRepository.transferMoney(1L, 2L, new BigDecimal("100.0"));

        var account1 = entityManager.find(Account.class, 1L);
        var account2 = entityManager.find(Account.class, 2L);

        Assertions.assertEquals(new BigDecimal("0.0"), account1.getBalance());
        Assertions.assertEquals(new BigDecimal("300.0"), account2.getBalance());
    }

    @Test
    void givenNotEnoughBalance_whenTransferMoney_thenFail() {
        Assertions.assertThrows(Throwable.class, () -> accountRepository.transferMoney(1L, 2L, new BigDecimal("1000.0")));
    }
}
