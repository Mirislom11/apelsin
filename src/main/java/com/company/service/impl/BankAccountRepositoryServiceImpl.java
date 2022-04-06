package com.company.service.impl;

import com.company.exceptions.ItemNotFoundException;
import com.company.model.domain.BankAccountEntity;
import com.company.model.enums.BankAccountStatus;
import com.company.repository.bankAccount.BankAccountRepository;
import com.company.service.BankAccountRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Log4j2
public class BankAccountRepositoryServiceImpl implements BankAccountRepositoryService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Override
    public BankAccountEntity findById(Long id) {
        Optional<BankAccountEntity> optional =bankAccountRepository.findById(BankAccountStatus.DELETED, id);
        if (optional.isPresent()) {
            return optional.get();
        }
        log.error("BankAccountRepositoryServiceImpl.findById {} ", id);
        throw new ItemNotFoundException("Bank Account by id not found");
    }
}
