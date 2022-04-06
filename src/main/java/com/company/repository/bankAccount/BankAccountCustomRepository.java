package com.company.repository.bankAccount;


import com.company.model.domain.BankAccountEntity;
import com.company.model.domain.BankEntity;
import com.company.model.domain.profile.ProfileEntity;
import com.company.model.enums.BankAccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BankAccountCustomRepository {
    @Autowired
    private EntityManager entityManager;
    public List<BankAccountEntity> findAll(BankAccountStatus status, BankEntity bank, ProfileEntity profile, String password, String number){
        StringBuilder queryStringBuilder = new StringBuilder("SELECT bc FROM BankAccountEntity bc ");
        StringBuilder paramStringBuilder = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        if (Objects.nonNull(status)) {
            paramStringBuilder.append(" WHERE status <> :status");
            params.put("status", status);
        }
        if(Objects.nonNull(bank)) {
            if (paramStringBuilder.length()> 0){
                paramStringBuilder.append(" AND bankEntity = :bank");
            }else {
                paramStringBuilder.append(" WHERE bankEntity = :bank");
            }
            params.put("bank", bank);
        }
        if (Objects.nonNull(profile)) {
            if (paramStringBuilder.length() > 0) {
                paramStringBuilder.append(" AND profile = :profile");
            }else {
                paramStringBuilder.append(" WHERE profile = :profile");
            }
            params.put("profile", profile);
        }
        if (Objects.nonNull(password)) {
            if (paramStringBuilder.length() > 0) {
                paramStringBuilder.append(" AND password = :password");
            }else {
                paramStringBuilder.append(" WHERE password = :password");
            }
            params.put("password", password);
        }
        if (Objects.nonNull(number)) {
            if (paramStringBuilder.length() > 0) {
                paramStringBuilder.append(" AND number = :number");
            }else {
                paramStringBuilder.append(" WHERE number = :number");
            }
            params.put("number", number);
        }
        queryStringBuilder.append(paramStringBuilder);
        Query query = entityManager.createQuery(queryStringBuilder.toString());
        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
            query.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return query.getResultList();
    }
}
