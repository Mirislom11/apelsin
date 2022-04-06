package com.company.service;

import com.company.model.dto.BankAccountDTO;
import com.company.model.dto.BankDTO;

import java.util.List;

public interface BankAccountService {
    BankAccountDTO save(BankAccountDTO bankAccountDTO);
    BankAccountDTO findById(Long id);
    List<BankAccountDTO> findAll(String cardNumber, String password, Long userId, Long bankId);
    BankAccountDTO update(Long id, BankAccountDTO bankAccountDTO);
    void delete(long id);
}
