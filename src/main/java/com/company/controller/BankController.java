package com.company.controller;

import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.model.dto.BankDTO;
import com.company.model.response.ApiResponse;
import com.company.service.BankService;
import com.company.service.impl.BankServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/bank")
public class BankController {
    @Autowired
    private BankServiceImpl bankService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid BankDTO bankDTO) {
        BankDTO responseBankDTO = bankService.save(bankDTO);
        ApiResponse<BankDTO> apiResponse = new ApiResponse<>("Bank success saved", responseBankDTO, HttpStatus.OK.value());;
        log.debug(apiResponse.getCode() + " saving bank " + apiResponse.getData());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        ApiResponse<BankDTO> apiResponse = new ApiResponse<>("Bank founded", bankService.findById(id), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "phone", required = false) String phone) {
        ApiResponse<List<BankDTO>> apiResponse = new ApiResponse<>("All bank", bankService.findAll(name, phone), HttpStatus.OK.value());
        log.debug(apiResponse.getCode() + "find all bank");
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody @Valid BankDTO bankDTO) {
        ApiResponse<BankDTO> apiResponse = new ApiResponse<>("Success updated", bankService.update(id, bankDTO), HttpStatus.OK.value());;
        log.debug(apiResponse.getCode() + "update bank by id{}", id);
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") @Valid @Positive long id) {
        bankService.delete(id);
        ApiResponse<BankDTO> apiResponse = new ApiResponse<>("Success deleted",  HttpStatus.OK.value());
        log.debug(apiResponse.getCode() + " deleted bank by id{}", id);
        return ResponseEntity.ok(apiResponse);
    }
}
