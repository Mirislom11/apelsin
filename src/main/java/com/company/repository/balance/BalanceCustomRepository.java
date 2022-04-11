package com.company.repository.balance;

import com.company.model.domain.BalanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BalanceCustomRepository {
    @Autowired
    private EntityManager entityManager;
    public List<BalanceEntity> findAll(Long minAmount, Long maxAmount, Long accountId){
        StringBuilder queryStringBuilder = new StringBuilder("SELECT be FROM BalanceEntity be ");
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
            if (paramStringBuilder.length() > 0) {
                paramStringBuilder.append(" AND amount <= :maxAmount");
            }else {
                paramStringBuilder.append(" WHERE amount <= :maxAmount");
            }
            params.put("maxAmount", maxAmount);
        }
        if (Objects.nonNull(accountId)) {
            if (paramStringBuilder.length() > 0) {
                paramStringBuilder.append(" AND bankAccount.id = :accountId");
            }else {
                paramStringBuilder.append(" WHERE bankAccount.id = :accountId");
            }
            params.put("accountId", accountId);
        }
        queryStringBuilder.append(paramStringBuilder);
        Query query = entityManager.createQuery(queryStringBuilder.toString());
        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
            query.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return query.getResultList();
    }
}
