package com.company.service;

import com.company.model.domain.BalanceEntity;

public interface BalanceRepositoryService {
    BalanceEntity findById(long id);
    BalanceEntity findByAccountId(Long bankAccountId);
}
