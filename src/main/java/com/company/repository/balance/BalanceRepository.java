package com.company.repository.balance;

import com.company.model.domain.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {
    boolean existsByBankAccountId(Long id);
    @Query(value = "SELECT amount FROM balances WHERE balances.bank_account = :bankAccount", nativeQuery = true)
    Long getAmountOfBalance(@RequestParam("bankAccount") Long bankAccount);
    boolean existsByBankAccount(Long bankAccount);
    /*@Query("UPDATE BalanceEntity b SET b.amount = b.amount + :amount WHERE b.bankAccount.id = :id")
    void updateBalance(@RequestParam("amount") Long amount, @RequestParam("id") Long id);*/
    Optional<BalanceEntity> findByBankAccountId(Long bankAccountId);
    @Modifying
    @Transactional
    void deleteByBankAccountId(Long id);
}
