package com.company.service;

import com.company.model.domain.BankAccountEntity;

public interface BankAccountRepositoryService {
    BankAccountEntity findById(Long id);
}
