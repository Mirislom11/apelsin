package com.company.service.impl;

import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.model.domain.BankAccountEntity;
import com.company.model.domain.PaymentEntity;
import com.company.model.dto.PaymentDTO;
import com.company.repository.balance.BalanceRepository;
import com.company.repository.payment.PaymentCustomRepository;
import com.company.repository.payment.PaymentRepository;
import com.company.service.BankAccountRepositoryService;
import com.company.service.PaymentService;
import com.company.utils.DateTimeUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private BalanceServiceImpl balanceService;
    @Autowired
    private BankAccountRepositoryService bankAccountRepositoryService;
    @Autowired
    private PaymentCustomRepository paymentCustomRepository;

    @Override
    @Transactional
    public PaymentDTO save(PaymentDTO paymentDTO) {
        log.debug("make payment {}", paymentDTO);
        BankAccountEntity fromAccount = bankAccountRepositoryService.findById(paymentDTO.getFromAccount());
        BankAccountEntity toAccount = bankAccountRepositoryService.findById(paymentDTO.getToAccount());
        if (!Objects.nonNull(fromAccount) || !Objects.nonNull(toAccount)) {
            log.error("account not found {} {}", paymentDTO.getFromAccount(), paymentDTO.getToAccount());
            throw new ItemNotFoundException("from or to account null");
        }
        Long fromBalance = balanceRepository.getAmountOfBalance(paymentDTO.getFromAccount());
        if (fromBalance < paymentDTO.getAmount()) {
            log.error("not enough amount {} ", paymentDTO.getAmount());
            throw new BadRequestException("payment amount not enough");
        }
        balanceService.update(fromAccount.getId(), paymentDTO.getAmount() * -1);
        balanceService.update(toAccount.getId(), paymentDTO.getAmount());
        PaymentEntity response = paymentRepository.save(paymentDTO.map2Entity(fromAccount, toAccount));
        return response.map2DTO();
    }

//    @Override
//    public PaymentDTO findById(String id) {
//        log.debug("find payment by id {} ", id);
//        Optional<PaymentEntity> optional = paymentRepository.findById(id);
//        if (optional.isPresent()) {
//            return optional.get().map2DTO();
//        }
//        log.error("payment by this is not found {} ", id);
//        throw new ItemNotFoundException("Payment by this id not found");
//    }

    @Override
    public PaymentDTO findById(String id) {
        log.debug("find payment by id {} ", id);
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Payment not found by id: " + id))
                .map2DTO();
    }

//    @Override
//    public List<PaymentDTO> findAll(Long minAmount, Long maxAmount, String beginCreatedDate, String endCreatedDate, Long fromAccount, Long toAccount) {
//        log.debug("find all payments");
//        List<PaymentEntity> paymentEntities = paymentCustomRepository.findAll(minAmount, maxAmount, DateTimeUtil.stringToLocalDate(beginCreatedDate), DateTimeUtil.stringToLocalDate(endCreatedDate),
//                fromAccount, toAccount);
//        return paymentEntities.stream()
//                .map(PaymentEntity::map2DTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<PaymentDTO> findAll(Long minAmount, Long maxAmount, String beginCreatedDate, String endCreatedDate, Long fromAccount, Long toAccount) {
        log.debug("find all payments");
        LocalDate bCreatedDate = null;
        LocalDate eCreatedDate = null;
        if(Objects.nonNull(beginCreatedDate)){
            bCreatedDate = DateTimeUtil.stringToLocalDate(beginCreatedDate);
        }
        if (Objects.nonNull(endCreatedDate)) {
            eCreatedDate = DateTimeUtil.stringToLocalDate(beginCreatedDate);
        }

        return paymentCustomRepository.findAll(minAmount, maxAmount, bCreatedDate,
                        eCreatedDate,
                        fromAccount, toAccount)
                .stream()
                .map(PaymentEntity::map2DTO)
                .collect(Collectors.toList());
    }

}
