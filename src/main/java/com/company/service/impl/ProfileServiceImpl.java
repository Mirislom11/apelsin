package com.company.service.impl;

import com.company.exceptions.BadRequestException;
import com.company.exceptions.ForbiddenException;
import com.company.exceptions.ItemNotFoundException;
import com.company.model.domain.profile.ProfileEntity;
import com.company.model.dto.ProfileDTO;
import com.company.model.enums.ProfileStatus;
import com.company.repository.ProfileCustomRepository;
import com.company.repository.profile.ProfileRepository;
import com.company.repository.profile.ProfileHistoryRepository;
import com.company.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileHistoryRepository profileHistoryRepository;
    @Autowired
    private ProfileRepositoryServiceImpl profileRepositoryService;
    @Autowired
    ProfileCustomRepository profileCustomRepository;

    @Override
    public ProfileDTO save(ProfileDTO profileDTO) {

        if (profileRepository.existsByEmailOrLogin(profileDTO.getEmail(), profileDTO.getLogin())) {
            log.error("ProfileServiceImpl.save exists login{} or email{}", profileDTO.getLogin(), profileDTO.getEmail());
            throw new BadRequestException("profile by this login or email exist");
        }
        ProfileEntity profile = profileDTO.map2Entity();
        profileRepository.save(profile);
        log.debug("ProfileServiceImpl.save profile{}", profileDTO);
        return profile.map2DTO();
    }

    @Override
    public ProfileDTO findById(long id) {
        Optional<ProfileEntity> optional = profileRepository.findById(ProfileStatus.DELETED, id);
        if (optional.isPresent()){
            log.debug("ProfileServiceImpl.findById profile by this id{}", optional.get().map2DTO());
            return optional.get().map2DTO();
        }
        log.error("ProfileServiceImpl.findById {} not found", id);
        throw new ItemNotFoundException("Profile by this id not found");
    }

    @Override
    public List<ProfileDTO> findAll(String name, String lastName, String login) {
        List<ProfileEntity> profileEntityList = profileCustomRepository.findAll(ProfileStatus.DELETED, name, lastName, login);
        List<ProfileDTO> profileDTOS = profileEntityList.stream()
                                            .map(ProfileEntity::map2DTO)
                                            .collect(Collectors.toList());
        log.debug("ProfileServiceImpl.findAll profiles{}", profileDTOS);
        return profileDTOS;
    }

    @Override
    @Transactional
    public ProfileDTO updateById(long id, ProfileDTO profileDTO) {
        ProfileEntity profile = profileRepositoryService.findById(id);
        if (Objects.nonNull(profileDTO.getName())) {
            profile.setName(profileDTO.getName());
        }
        if (Objects.nonNull(profileDTO.getSurname())) {
            profile.setLastName(profileDTO.getSurname());
        }
        if (Objects.nonNull(profileDTO.getLogin())) {
            profile.setLogin(profileDTO.getLogin());
        }
        profileHistoryRepository.save(profile.map2ProfileHistory());
        profile.setLastModifiedDate(LocalDateTime.now());
        profile.setVersion(profile.getVersion() + 1);
        ProfileEntity response = profileRepository.save(profile);
        log.debug(" updating profile dto {}", response.map2DTO());
        return response.map2DTO();
    }

    @Transactional
    @Override
    public ProfileDTO deleteById(long id) {
        ProfileEntity profile = profileRepositoryService.findById(id);
        profileHistoryRepository.save(profile.map2ProfileHistory());
        profile.setLastModifiedDate(LocalDateTime.now());
        profile.setVersion(profile.getVersion() + 1);
        profile.setStatus(ProfileStatus.DELETED);
        ProfileEntity response = profileRepository.save(profile);
        log.debug("profile by id{} deleted ", id);
        return response.map2DTO();
    }

}
