package com.company.repository;

import com.company.model.domain.profile.ProfileEntity;
import com.company.model.enums.ProfileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ProfileCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> findAll(ProfileStatus profileStatus, String name, String lastName, String login) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder("SELECT p FROM ProfileEntity p ");
        StringBuilder paramStringBuilder = new StringBuilder("");

        if (Objects.nonNull(profileStatus)) {
            paramStringBuilder.append(" WHERE status <> :status");
            params.put("status", profileStatus);
        }

        if (Objects.nonNull(name)) {
            if (paramStringBuilder.length()> 0) {
                paramStringBuilder.append(" AND name = :name");
            }else {
                paramStringBuilder.append(" WHERE name = :name");
            }
            params.put("name", name);
        } if (Objects.nonNull(lastName)) {
            if (paramStringBuilder.length()>0) {
                paramStringBuilder.append(" AND lastName = :lastName");
            }else {
                paramStringBuilder.append(" WHERE lastName = :lastName");
            }
            params.put("lastName", lastName);
        } if (Objects.nonNull(login)){
            if (paramStringBuilder.length()>0) {
                stringBuilder.append(" AND login = :login");
            }else {
                stringBuilder.append(" WHERE login = :login");
            }
            params.put("login", login);
        }
        stringBuilder.append(paramStringBuilder);
        Query query =entityManager.createQuery(stringBuilder.toString());
        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        return query.getResultList();
    }
}
