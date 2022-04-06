package com.company.service;

import com.company.model.domain.BankEntity;

public interface BankRepositoryService {
    BankEntity findById(long id);
}
