package com.company.model.domain;

import com.company.model.dto.PaymentDTO;
import com.company.utils.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(generator = "system_uuid")
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @JoinColumn(name = "from_account")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private BankAccountEntity fromAccount;
    @JoinColumn(name = "to_account")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private BankAccountEntity toAccount;
    @Column(name = "amount")
    private Long amount;
    @Column(name = "created_date")
    private LocalDateTime createdDateTime = LocalDateTime.now();

    public PaymentDTO map2DTO () {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(this.getId());
        paymentDTO.setAmount(this.getAmount());
        paymentDTO.setCreatedDate(DateTimeUtil.LocalDateTimeToString(this.getCreatedDateTime()));
        paymentDTO.setFromAccount(this.getFromAccount().getId());
        paymentDTO.setToAccount(this.getToAccount().getId());
        return paymentDTO;
    }
}
