package com.company.service;

import com.company.model.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
    PaymentDTO save(PaymentDTO paymentDTO);

    PaymentDTO findById(String id);

    List<PaymentDTO> findAll(Long minAmount, Long maxAmount, String beginCreatedDate, String endCreatedDate, Long fromAccount, Long toAccount);

}
