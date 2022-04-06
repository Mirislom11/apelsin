package com.company.service.impl;

import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.UnauthorizedException;
import com.company.model.domain.profile.ProfileEntity;
import com.company.model.enums.ProfileStatus;
import com.company.repository.profile.ProfileRepository;
import com.company.service.ProfileRepositoryService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ProfileRepositoryServiceImpl implements ProfileRepositoryService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public ProfileEntity findByLoginAndPassword(String login, String password) {
        Optional<ProfileEntity> optional = profileRepository.findByLoginAndPassword(ProfileStatus.DELETED, login, password);
        if (optional.isPresent()) {
            log.debug("ProfileRepositoryServiceImpl.findByLoginPassword success {}", login);
            return optional.get();
        }
        log.error("ProfileRepositoryServiceImpl.findByLoginPassword fail {}", login);
        throw new UnauthorizedException("Profile by this login password not found");
    }


    @Override
    public ProfileEntity findById(long id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        log.error("ProfileRepositoryServiceImpl.findById {} ", id);
        throw new ItemNotFoundException("Profile by this id not found");
    }
}
