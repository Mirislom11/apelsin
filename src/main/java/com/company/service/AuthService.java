package com.company.service;

import com.company.model.response.ApiResponse;
import com.company.model.vm.AuthVM;

public interface AuthService {
    AuthVM authorization(AuthVM authVM);
}
