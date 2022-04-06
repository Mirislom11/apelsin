package com.company.service.impl;

import com.company.exceptions.ItemNotFoundException;
import com.company.model.domain.BankEntity;
import com.company.model.enums.BankStatus;
import com.company.repository.bank.BankRepository;
import com.company.service.BankRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class BankRepositoryServiceImpl implements BankRepositoryService {
    @Autowired
    private BankRepository bankRepository;
    @Override
    public BankEntity findById(long id) {
        Optional<BankEntity> optional = bankRepository.findById(BankStatus.DELETED, id);
        if (optional.isPresent()){
            log.debug("bank by id founded{}", optional.get());
            return optional.get();
        }
        log.error("BankRepositoryServiceImpl.findById {}", id);
        throw new ItemNotFoundException("bank by this id not found");
    }
}
