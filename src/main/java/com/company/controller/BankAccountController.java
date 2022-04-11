package com.company.controller;

import com.company.model.dto.BankAccountDTO;
import com.company.model.dto.BankDTO;
import com.company.model.response.ApiResponse;
import com.company.service.impl.BankAccountServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/bank-account")
public class BankAccountController {
    @Autowired
    private BankAccountServiceImpl bankAccountService;

    @PostMapping
    public ResponseEntity<?> save (@RequestBody @Valid BankAccountDTO bankAccountDTO) {
       log.debug("BankAccountController.save{}", bankAccountDTO);
        BankAccountDTO response = bankAccountService.save(bankAccountDTO);
        ApiResponse<BankAccountDTO> apiResponse = new ApiResponse<>("Success saved", response, HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        log.debug("BankAccountController.findById {}", id);
        BankAccountDTO response = bankAccountService.findById(id);
        ApiResponse<BankAccountDTO> apiResponse = new ApiResponse<>("Success founded", response, HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "number", required = false) String number,
                                     @RequestParam(name = "password", required = false) String password,
                                     @RequestParam(name = "profileId", required = false) Long profileId,
                                     @RequestParam(name = "bankId", required = false) Long bankId) {
        log.debug("BankAccountController.findAll");
        List<BankAccountDTO> response = bankAccountService.findAll(number, password, profileId, bankId);
        ApiResponse<List<BankAccountDTO>> apiResponse = new ApiResponse<>("All bank accounts", response, HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody  BankAccountDTO bankAccountDTO) {
        log.debug("BankAccountController.update {}", bankAccountDTO);
        BankAccountDTO response = bankAccountService.update(id, bankAccountDTO);
        ApiResponse<BankAccountDTO> apiResponse = new ApiResponse<>("Update bank Account", response, HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        log.debug("BankAccountController.delete {}", id);
        bankAccountService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Delete successfully", HttpStatus.OK.value()));
    }


}
