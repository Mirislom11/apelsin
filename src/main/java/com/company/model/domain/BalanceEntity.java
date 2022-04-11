package com.company.model.domain;

import com.company.model.dto.BalanceDTO;
import com.company.model.enums.BalanceStatus;
import com.company.utils.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "balances")
public class BalanceEntity extends BaseEntity{
    @Column(name = "amount", length = 32, nullable = false)
    private long amount;
    @JoinColumn(name = "bank_account")
    @OneToOne(cascade = CascadeType.ALL)
    private BankAccountEntity bankAccount;
    @Column(name = "balance_status")
    @Enumerated(EnumType.STRING)
    private BalanceStatus status;
    public BalanceDTO mapToDTO () {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setId(super.getId());
        balanceDTO.setCreatedDateTime(DateTimeUtil.LocalDateTimeToString(super.getCreatedDateTime()));
        balanceDTO.setLastModifiedDateTime(DateTimeUtil.LocalDateTimeToString(super.getLastModifiedDate()));
        balanceDTO.setBankAccountId(bankAccount.getId());
        balanceDTO.setAmount(this.getAmount());
        balanceDTO.setStatus(this.getStatus());
        return balanceDTO;
    }
}
