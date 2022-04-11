package com.company.service;

import com.company.model.domain.BankAccountEntity;
import com.company.model.dto.BalanceDTO;

import java.util.List;

public interface BalanceService {
    BalanceDTO save(BalanceDTO balanceDTO, BankAccountEntity bankAccount);
    List<BalanceDTO> findAll(Long bankAccountId, Long minAmount, Long maxAmount);
    BalanceDTO findById(Long id);
    BalanceDTO update(Long bankAccountId, Long amount);
    void delete (Long id);
    void deleteByAccountId (Long id);
}
