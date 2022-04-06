package com.company.model.domain;

import com.company.model.domain.profile.ProfileEntity;
import com.company.model.dto.BankAccountDTO;
import com.company.model.enums.BankAccountStatus;
import com.company.utils.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "bank_accounts")
public class BankAccountEntity extends BaseEntity{
    @Column(name = "account_number", length = 32, unique = true, nullable = false)
    private String number;
    @Column(name = "password", length = 32, nullable = false)
    private String password;
    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    private BankAccountStatus status;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ProfileEntity profile;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private BankEntity bankEntity;

    public BankAccountDTO map2DTO(){
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(super.getId());
        bankAccountDTO.setAccountNumber(this.getNumber());
        bankAccountDTO.setPassword(this.getPassword());
        bankAccountDTO.setProfileId(this.getProfile().getId());
        bankAccountDTO.setBankId(this.getBankEntity().getId());
        bankAccountDTO.setBankAccountStatus(this.getStatus());
        bankAccountDTO.setCreatedDateTime(DateTimeUtil.LocalDateTimeToString(this.getCreatedDateTime()));
        bankAccountDTO.setLastModifiedDate(DateTimeUtil.LocalDateTimeToString(this.getLastModifiedDate()));
        bankAccountDTO.setVersion(super.getVersion());
        return bankAccountDTO;
    }
}
