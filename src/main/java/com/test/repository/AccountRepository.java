package com.test.repository;

import com.test.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final EntityManager em;

    @Transactional
    public void updateBalanceForAllAccounts() {
        em.createNativeQuery("select * from account for update").getResultList();

        em.createNativeQuery("update account set balance = least(balance * 1.1, max_balance)")
                .executeUpdate();
    }

    @Transactional
    public void transferMoney(Long userIdFrom, Long userIdTo, BigDecimal amount) {
        em.createNativeQuery("select * from account where user_id in (:id1, :id2) for update")
                .setParameter("id1", userIdFrom)
                .setParameter("id2", userIdTo)
                .getResultList();

        em.createNativeQuery("update account set balance = balance + :amount where user_id = :id2")
                .setParameter("amount", amount)
                .setParameter("id2", userIdTo)
                .executeUpdate();
        em.createNativeQuery("update account set balance = balance - :amount where user_id = :id1")
                .setParameter("amount", amount)
                .setParameter("id1", userIdFrom)
                .executeUpdate();
    }

    public List<Account> findAll() {
        return em.createQuery("select a from Account a", Account.class).getResultList();
    }
}
