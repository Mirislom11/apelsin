package com.company.model.dto;

import com.company.model.domain.BalanceEntity;
import com.company.model.domain.BankAccountEntity;
import com.company.model.enums.BalanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
    private long id;
    @NotNull(message = "amount of balance should not be null or empty")
    private Long amount;
    private Long bankAccountId;
    private String createdDateTime;
    private String lastModifiedDateTime;
    private BalanceStatus status;
    public BalanceEntity map2Entity(BankAccountEntity bankAccount) {
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setVersion(1l);
        balanceEntity.setAmount(this.getAmount());
        balanceEntity.setBankAccount(bankAccount);
        balanceEntity.setStatus(BalanceStatus.ACTIVE);
        return balanceEntity;
    }
}
