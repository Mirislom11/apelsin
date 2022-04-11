package com.company.service.impl;

import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.model.domain.BankAccountEntity;
import com.company.model.domain.BankEntity;
import com.company.model.domain.profile.ProfileEntity;
import com.company.model.dto.BankAccountDTO;
import com.company.model.enums.BankAccountStatus;
import com.company.repository.bankAccount.BankAccountCustomRepository;
import com.company.repository.bankAccount.BankAccountRepository;
import com.company.service.BankAccountRepositoryService;
import com.company.service.BankAccountService;
import com.company.service.ProfileRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private ProfileRepositoryServiceImpl profileRepositoryService;
    @Autowired
    private BankRepositoryServiceImpl bankRepositoryService;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankAccountCustomRepository bankAccountCustomRepository;
    @Autowired
    private BankAccountRepositoryService bankAccountRepositoryService;
    @Autowired
    private BalanceServiceImpl balanceService;
    @Override
    @Transactional
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) {
        log.debug("saving bank account {} ", bankAccountDTO);
        if (!Objects.nonNull(bankAccountDTO.getBalanceDTO())) throw new BadRequestException("Balance is null");
        boolean existsAccount = bankAccountRepository.existsByNumber(bankAccountDTO.getAccountNumber());
        if (existsAccount) throw new BadRequestException("Bank by this number exists");
        BankEntity bank = bankRepositoryService.findById(bankAccountDTO.getBankId());
        ProfileEntity profile = profileRepositoryService.findById(bankAccountDTO.getProfileId());
        BankAccountEntity bankAccount = bankAccountDTO.map2Entity(bank,profile);
        bankAccountRepository.save(bankAccount);
        balanceService.save(bankAccountDTO.getBalanceDTO(), bankAccount);
        return bankAccount.map2DTO();
    }

    @Override
    public BankAccountDTO findById(Long id) {
        BankAccountDTO bankAccountDTO = bankAccountRepositoryService.findById(id).map2DTO();
        log.debug("BankAccountServiceImpl.findById{}", bankAccountDTO);
        return bankAccountDTO;
    }

    @Override
    public List<BankAccountDTO> findAll(String cardNumber, String password, Long profileId, Long bankId) {
        BankEntity bank =null;
        ProfileEntity profile = null;
        if (Objects.nonNull(bankId)) {
            bank = bankRepositoryService.findById(bankId);
        }
        if (Objects.nonNull(profileId)) {
            profile = profileRepositoryService.findById(profileId);
        }
        List<BankAccountEntity> bankAccountEntities = bankAccountCustomRepository.findAll(BankAccountStatus.DELETED, bank, profile, password,cardNumber);
        log.debug("BankAccountServiceImpl.findAll");
        return bankAccountEntities.stream()
                .map(BankAccountEntity::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO update(Long id, BankAccountDTO bankAccountDTO) {
        BankAccountEntity bankAccount = bankAccountRepositoryService.findById(id);
        if (Objects.nonNull(bankAccountDTO.getAccountNumber())) {
            bankAccount.setNumber(bankAccountDTO.getAccountNumber());
        }
        if (Objects.nonNull(bankAccountDTO.getPassword())) {
            bankAccount.setPassword(bankAccountDTO.getPassword());
        }
        bankAccount.setVersion(bankAccount.getVersion()+1);
        BankAccountEntity response = bankAccountRepository.save(bankAccount);
        log.debug("update account {} ", response);
        return response.map2DTO();
    }

    @Override
    public void delete(long id) {
        BankAccountEntity bankAccountEntity = bankAccountRepositoryService.findById(id);
        bankAccountEntity.setStatus(BankAccountStatus.DELETED);
        bankAccountEntity.setVersion(bankAccountEntity.getVersion()+1);
        log.debug("delete account by id {} ", id);
        balanceService.deleteByAccountId(id);
        bankAccountRepository.save(bankAccountEntity);

    }
}
