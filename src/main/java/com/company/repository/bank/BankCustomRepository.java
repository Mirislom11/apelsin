package com.company.repository.bank;

import com.company.model.domain.BankEntity;
import com.company.model.enums.BankAccountStatus;
import com.company.model.enums.BankStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BankCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<BankEntity> findAll(BankStatus status, String name, String phone) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT b FROM BankEntity b ");
        StringBuilder paramStringBuilder = new StringBuilder("");
        if (Objects.nonNull(status)) {
            if (paramStringBuilder.length()>0) {
                paramStringBuilder.append(" AND b.status <> :status");
            }else {
                paramStringBuilder.append(" WHERE b.status <> :status");
            }
            params.put("status", status);
        }
        if (Objects.nonNull(name)) {
            if (paramStringBuilder.length()> 0) {
                paramStringBuilder.append(" AND b.name like :name");
            }else {
                paramStringBuilder.append(" WHERE b.name like :name");
            }
            params.put("name", name + "%");
        }
        if (Objects.nonNull(phone)) {
            if (paramStringBuilder.length()> 0){
                paramStringBuilder.append(" AND b.phone = :phone");
            }else {
                paramStringBuilder.append(" WHERE b.phone = :phone");
            }
            params.put("phone", "+" + phone);
        }
        stringBuilder.append(paramStringBuilder);
        Query query = entityManager.createQuery(stringBuilder.toString());
        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
            query.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return query.getResultList();
    }
}
