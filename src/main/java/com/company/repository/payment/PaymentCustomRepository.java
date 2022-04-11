package com.company.repository.payment;

import com.company.model.domain.PaymentEntity;
import com.company.service.BankAccountRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class PaymentCustomRepository {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BankAccountRepositoryService bankAccountRepositoryService;

    public List<PaymentEntity> findAll (Long minAmount, Long maxAmount, LocalDate beginDate, LocalDate endDate, Long fromAccount, Long toAccount){
        StringBuilder queryStringBuilder = new StringBuilder("SELECT p FROM PaymentEntity p ");
        StringBuilder paramStringBuilder = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        if (Objects.nonNull(minAmount)) {
            if (paramStringBuilder.length() > 0) {
                paramStringBuilder.append(" AND amount >= :minAmount");
            }else {
                paramStringBuilder.append(" WHERE amount >= :minAmount");
            }
            params.put("minAmount", minAmount);
        }
        if (Objects.nonNull(maxAmount)) {
            if (paramStringBuilder.length()>0) {
                paramStringBuilder.append(" AND amount <= :maxAmount");
            }else {
                paramStringBuilder.append(" WHERE amount <= :maxAmount");
            }
            params.put("maxAmount", maxAmount);
        }
        if (Objects.nonNull(beginDate)) {
            if (paramStringBuilder.length()>0) {
                paramStringBuilder.append(" AND createdDateTime >= :beginDate");
            }else {
                paramStringBuilder.append(" WHERE createdDateTime >= :beginDate");
            }
            params.put("beginDate", LocalDateTime.of(beginDate, LocalTime.MIN));
        }
        if (Objects.nonNull(endDate)) {
            if (paramStringBuilder.length()>0) {
                paramStringBuilder.append(" AND createdDateTime <= :endDate");
            }else {
                paramStringBuilder.append(" WHERE createdDateTime <= :endDate");
            }
            params.put("endDate", LocalDateTime.of(endDate, LocalTime.MAX));
        }
        if (Objects.nonNull(fromAccount)) {
            if (paramStringBuilder.length() > 0 ) {
                paramStringBuilder.append(" AND fromAccount = :fromAccount");
            }else {
                paramStringBuilder.append(" WHERE fromAccount = :fromAccount");
            }
            params.put("fromAccount", bankAccountRepositoryService.findById(fromAccount));
        }
        if (Objects.nonNull(toAccount)) {
            if (paramStringBuilder.length() > 0) {
                paramStringBuilder.append(" AND toAccount = :toAccount");
            }else {
                paramStringBuilder.append(" WHERE toAccount = :toAccount");
            }
            params.put("toAccount", bankAccountRepositoryService.findById(toAccount));
        }
        queryStringBuilder.append(paramStringBuilder);
        Query query =entityManager.createQuery(queryStringBuilder.toString());
        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        return query.getResultList();
    }
}
