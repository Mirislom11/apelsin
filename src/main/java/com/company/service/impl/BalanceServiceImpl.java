package com.company.service.impl;

import com.company.exceptions.BadRequestException;
import com.company.model.domain.BalanceEntity;
import com.company.model.domain.BankAccountEntity;
import com.company.model.dto.BalanceDTO;
import com.company.model.enums.BalanceStatus;
import com.company.repository.balance.BalanceCustomRepository;
import com.company.repository.balance.BalanceRepository;
import com.company.service.BalanceRepositoryService;
import com.company.service.BalanceService;
import com.company.service.BankAccountRepositoryService;
import com.company.service.BankAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BalanceServiceImpl implements BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private BalanceRepositoryService balanceRepositoryService;
    @Autowired
    private BalanceCustomRepository balanceCustomRepository;
    @Override
    @Transactional
    public BalanceDTO save(BalanceDTO balanceDTO, BankAccountEntity bankAccount) {
        log.debug("BalanceServiceImpl.save balance {}", balanceDTO);
        if (balanceRepository.existsByBankAccountId(bankAccount.getId())){
            throw new BadRequestException("Balance by this bank account exist");
        }
        BalanceEntity balanceEntity = balanceDTO.map2Entity(bankAccount);
        balanceRepository.save(balanceEntity);
        return balanceEntity.mapToDTO();
    }

    @Override
    public List<BalanceDTO> findAll(Long bankAccountId, Long minAmount, Long maxAmount) {
        List<BalanceEntity> balanceEntityList = balanceCustomRepository.findAll(minAmount, maxAmount, bankAccountId);
        return balanceEntityList.stream()
                .map(BalanceEntity::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BalanceDTO findById(Long id) {
        return balanceRepositoryService.findById(id).mapToDTO();
    }

    @Override
    public BalanceDTO update(Long bankAccountId, Long amount) {
        log.debug("update balance by account id {} ", bankAccountId);
        BalanceEntity balanceEntity = balanceRepositoryService.findByAccountId(bankAccountId);
        balanceEntity.setAmount(balanceEntity.getAmount() + amount);
        balanceEntity.setVersion(balanceEntity.getVersion()+1);
        balanceEntity.setLastModifiedDate(LocalDateTime.now());
        balanceRepository.save(balanceEntity);
        log.debug("success balance change by accountId {} ", balanceEntity);
        return balanceEntity.mapToDTO();
    }

    @Override
    public void delete(Long id) {
        log.debug("BalanceServiceImpl.delete by id {}", id);
        BalanceEntity balanceEntity = balanceRepositoryService.findById(id);
        balanceEntity.setVersion(balanceEntity.getVersion()+1);
        balanceEntity.setStatus(BalanceStatus.DELETED);
        balanceEntity.setLastModifiedDate(LocalDateTime.now());
        balanceRepository.save(balanceEntity);
        log.debug("Success delete by id {} ", id);
    }

    @Override
    public void deleteByAccountId(Long id) {
        log.debug("delete balance by id {} ", id);
        BalanceEntity balance = balanceRepositoryService.findByAccountId(id);
        balance.setStatus(BalanceStatus.DELETED);
        balance.setVersion(balance.getVersion()+1);
        balance.setLastModifiedDate(LocalDateTime.now());
        balanceRepository.save(balance);
    }
}
