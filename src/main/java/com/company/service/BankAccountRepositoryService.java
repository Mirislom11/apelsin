package com.company.service;

import com.company.model.domain.BankAccountEntity;
import com.company.model.enums.BankAccountStatus;

public interface BankAccountRepositoryService {
    BankAccountEntity findById(Long id);
}
