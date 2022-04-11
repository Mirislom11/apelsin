package com.company.service.impl;

import com.company.exceptions.BadRequestException;
import com.company.model.domain.BankEntity;
import com.company.model.dto.BankDTO;
import com.company.model.enums.BankStatus;
import com.company.repository.bank.BankCustomRepository;
import com.company.repository.bank.BankRepository;
import com.company.service.BankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private BankRepositoryServiceImpl bankRepositoryServiceImpl;
    @Autowired
    private BankCustomRepository bankCustomRepository;

    @Override
    public BankDTO save(BankDTO bankDTO) {
        if (bankRepository.existsByPhoneOrName(bankDTO.getPhone(), bankDTO.getName())) {
            log.error("write exists bank phone or name{}", bankDTO.getPhone());
            throw new BadRequestException("Bank by this phone or name exists");
        }
        BankEntity bank = bankDTO.map2Entity();
        bankRepository.save(bank);
        log.debug("save bank {}", bank);
        return bank.map2DTO();
    }

    @Override
    public BankDTO findById(long id) {
        BankEntity bank = bankRepositoryServiceImpl.findById(id);
        return bank.map2DTO();
    }

    @Override
    public List<BankDTO> findAll(String name, String phone) {
        List<BankEntity> bankEntityList = bankCustomRepository.findAll(BankStatus.DELETED, name, phone);
        log.debug("find all bank");
        return bankEntityList.stream()
                .map(BankEntity::map2DTO)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public BankDTO update(long id, BankDTO bankDTO) {
        BankEntity bank = bankRepositoryServiceImpl.findById(id);
        bank.setPhone(bankDTO.getPhone());
        bank.setName(bankDTO.getName());
        bank.setLastModifiedDate(LocalDateTime.now());
        bank.setVersion(bank.getVersion() + 1);
        bankRepository.save(bank);
        return bank.map2DTO();
    }

    @Override
    public void delete(long id) {
        BankEntity bank = bankRepositoryServiceImpl.findById(id);
        bank.setStatus(BankStatus.DELETED);
        bank.setVersion(bank.getVersion() + 1);
        log.debug("deleted bank id{}", id);
        bankRepository.save(bank);
    }
}
