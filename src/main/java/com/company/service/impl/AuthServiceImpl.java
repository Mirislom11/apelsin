package com.company.service.impl;

import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.UnauthorizedException;
import com.company.model.domain.profile.ProfileEntity;
import com.company.model.dto.ProfileDTO;
import com.company.model.response.ApiResponse;
import com.company.model.vm.AuthVM;
import com.company.service.AuthService;
import com.company.service.ProfileRepositoryService;
import com.company.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private ProfileRepositoryService profileRepositoryService;
    @Override
    public AuthVM authorization(AuthVM authVM) {
        log.debug("AuthServiceImpl.authorization {}",authVM);
        ProfileEntity profile = profileRepositoryService.findByLoginAndPassword(authVM.getLogin(), authVM.getPassword());
        authVM.setJwt(JwtUtils.createJwt(profile.getId(), profile.getRole()));
        return authVM;

    }
}
