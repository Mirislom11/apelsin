package com.company.model.dto;

import com.company.model.domain.BankEntity;
import com.company.model.enums.BankStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankDTO {
    private long id;
    @NotEmpty(message = "name should ve not empty or null")
    @Size(max = 32, message = "name should be max 32")
    private String name;
    @NotEmpty(message = "phone should ve not empty or null")
    @Size(max = 32, message = "phone should be max 32")
    private String phone;
    private String createdDateTime;
    private String lastModifiedDate;
    private long version;
    private BankStatus status;
    public BankEntity map2Entity() {
        BankEntity bank = new BankEntity();
        bank.setName(this.getName());
        bank.setPhone(this.getPhone());
        bank.setVersion(1l);
        bank.setLastModifiedDate(LocalDateTime.now());
        bank.setCreatedDateTime(LocalDateTime.now());
        bank.setStatus(BankStatus.ACTIVE);
        return bank;
    }
}
