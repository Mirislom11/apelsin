package com.company.repository.bankAccount;

import com.company.model.domain.BankAccountEntity;
import com.company.model.enums.BankAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    @Query("SELECT bc FROM BankAccountEntity bc WHERE bc.status <> :status AND bc.id = :id")
    Optional<BankAccountEntity> findById(@Param("status")BankAccountStatus bankAccountStatus, @Param("id") Long id);
    boolean existsByNumber(String number);
    @Query("SELECT b FROM BankAccountEntity  b WHERE b.status <> :status AND b.id = :id")
    Optional<BankAccountEntity> findByStatusAndId(@RequestParam("status") BankAccountStatus status, @RequestParam("id")Long id);

}
