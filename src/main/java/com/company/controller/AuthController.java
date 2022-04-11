package com.company.controller;

import com.company.exceptions.ItemNotFoundException;
import com.company.model.response.ApiResponse;
import com.company.model.vm.AuthVM;
import com.company.service.impl.AuthServiceImpl;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;
    @PostMapping("/authorization")
    public ResponseEntity<?> authorization(@RequestBody @Valid AuthVM authVM) {
        log.debug("authorization user{}", authVM);
        ApiResponse<AuthVM> apiResponse = new ApiResponse<>("Success authorization", authService.authorization(authVM), HttpStatus.OK.value());;
        return ResponseEntity.ok(apiResponse);
    }
}
