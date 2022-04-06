package com.company.model.domain;

import com.company.model.dto.BankDTO;
import com.company.model.enums.BankStatus;
import com.company.utils.DateTimeUtil;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank")
public class BankEntity extends BaseEntity {
    @Column(name = "bank_name", nullable = false, unique = true, length = 32)
    private String name;
    @Column(name = "phone", nullable = false, length = 32, unique = true)
    private String phone;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BankStatus status;

    public BankDTO map2DTO() {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setId(super.getId());
        bankDTO.setLastModifiedDate(DateTimeUtil.LocalDateTimeToString(super.getLastModifiedDate()));
        bankDTO.setCreatedDateTime(DateTimeUtil.LocalDateTimeToString(super.getCreatedDateTime()));
        bankDTO.setVersion(super.getVersion());
        bankDTO.setPhone(this.getPhone());
        bankDTO.setName(this.getName());
        bankDTO.setStatus(this.getStatus());

        BankDTO.builder()
                .id(this.getId())
                .name(this.name)
                .build();

        return bankDTO;
    }
}
