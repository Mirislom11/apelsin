package com.company.model.dto;

import com.company.model.domain.BankAccountEntity;
import com.company.model.domain.BankEntity;
import com.company.model.domain.profile.ProfileEntity;
import com.company.model.enums.BankAccountStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountDTO {
    private long id;
    @Size(max = 8, message = "Account number should be maximum 8")
    private String accountNumber;
    @Size(max = 4, message = "Password size should be max 4")
    private String password;
    private BankAccountStatus bankAccountStatus;
    private BalanceDTO balanceDTO;
    private Long balanceId;
    @Positive
    private long profileId;
    @Positive
    private long bankId;
    private String createdDateTime;
    private String lastModifiedDate;
    private long version;

    public BankAccountEntity map2Entity (BankEntity bank, ProfileEntity profile) {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setNumber(this.getAccountNumber());
        bankAccountEntity.setBankEntity(bank);
        bankAccountEntity.setProfile(profile);
        bankAccountEntity.setPassword(this.getPassword());
        bankAccountEntity.setStatus(BankAccountStatus.ACTIVE);
        bankAccountEntity.setVersion(1l);
        return bankAccountEntity;
    }
}
