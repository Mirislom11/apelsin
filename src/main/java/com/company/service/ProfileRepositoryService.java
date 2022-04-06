package com.company.service;

import com.company.model.domain.profile.ProfileEntity;

import java.util.List;
import java.util.Optional;
public interface ProfileRepositoryService {

    ProfileEntity findByLoginAndPassword(String email, String password);
    ProfileEntity findById(long id);
}
