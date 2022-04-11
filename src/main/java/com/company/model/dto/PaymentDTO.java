package com.company.model.dto;

import com.company.model.domain.BalanceEntity;
import com.company.model.domain.BankAccountEntity;
import com.company.model.domain.PaymentEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PaymentDTO {
    private String id;
    @NotNull(message = "from account should not be null")
    private Long fromAccount;
    @NotNull(message = "to account should not be null")
    private Long toAccount;
    @NotNull(message = "amount should not be null")
    @Positive
    private Long amount;
    private String createdDate;

    public PaymentEntity map2Entity (BankAccountEntity fromAccount, BankAccountEntity toAccount) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(this.getAmount());
        paymentEntity.setToAccount(toAccount);
        paymentEntity.setFromAccount(fromAccount);
        return paymentEntity;
    }
}
