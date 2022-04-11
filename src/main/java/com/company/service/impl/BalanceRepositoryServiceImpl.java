package com.company.service.impl;

import com.company.exceptions.ItemNotFoundException;
import com.company.model.domain.BalanceEntity;
import com.company.repository.balance.BalanceRepository;
import com.company.service.BalanceRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class BalanceRepositoryServiceImpl implements BalanceRepositoryService {
    @Autowired
    BalanceRepository balanceRepository;
    @Override
    public BalanceEntity findById(long id) {
       log.debug("BalanceRepositoryServiceImpl.findById {}", id);
        Optional<BalanceEntity> optional = balanceRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        log.error("Balance by this id not found {} ", id);
        throw new ItemNotFoundException("Balance by this id not found");
    }

    @Override
    public BalanceEntity findByAccountId(Long bankAccountId) {
        log.debug("find balance by accountId {} ", bankAccountId);
        Optional<BalanceEntity> optional = balanceRepository.findByBankAccountId(bankAccountId);
        if (optional.isPresent()) {
            return optional.get();
        }
        log.error("Balance by this id bankAccountId found");
        throw new ItemNotFoundException("Balance by this bankAccountId not found");
    }
}
