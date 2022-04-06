package com.company.repository.bank;

import com.company.model.domain.BankEntity;
import com.company.model.enums.BankStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.*;
import java.util.*;
import java.util.List;

public interface BankRepository extends JpaRepository<BankEntity, Long> {
    boolean existsByPhoneOrName(String phone, String name);

    @Query("SELECT b FROM BankEntity b WHERE b.status <> :status")
    List<BankEntity> findAll(@Param("status")BankStatus status);
    @Query("SELECT b FROM BankEntity b WHERE b.status <> :status AND b.id = :id")
    Optional<BankEntity> findById(@Param("status") BankStatus status, @Param("id") long id);
}
