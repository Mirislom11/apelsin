package com.company.controller;

import com.company.model.dto.BalanceDTO;
import com.company.model.response.ApiResponse;
import com.company.service.impl.BalanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {
    @Autowired
    private BalanceServiceImpl balanceService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "bankAccountId", required = false) Long bankAccountId,
                                     @RequestParam(name = "minAmount", required = false) Long minAmount,
                                     @RequestParam(name = "maxAmount", required = false) Long maxAmount) {
        List<BalanceDTO> balanceDTOList = balanceService.findAll(bankAccountId, minAmount, maxAmount);
        return ResponseEntity.ok(new ApiResponse<>("Founded all balances", balanceDTOList, HttpStatus.OK.value()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        BalanceDTO balanceDTO = balanceService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>("Success find by id", balanceDTO, HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        balanceService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Successfully deleted", HttpStatus.OK.value()));
    }

}
