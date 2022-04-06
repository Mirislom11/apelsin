package com.company.repository.bank;

import com.company.model.domain.BankEntity;
import com.company.model.enums.BankStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BankCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<BankEntity> findAll(String name, String phone) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT b FROM BankEntity b WHERE b.status <> :status");
        params.put("status", BankStatus.DELETED);

        if (name != null) {
            stringBuilder.append(" AND b.name like :name");
            params.put("name", name + "%");
        }
        if (phone != null) {
            stringBuilder.append(" AND b.phone = :phone");
            params.put("phone", "+" + phone);
        }

        Query query = entityManager.createQuery(stringBuilder.toString());
        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
            query.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return query.getResultList();
    }
}
