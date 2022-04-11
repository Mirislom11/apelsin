package com.company.controller;

import com.company.model.dto.PaymentDTO;
import com.company.model.response.ApiResponse;
import com.company.service.PaymentService;
import com.company.service.impl.PaymentServiceImpl;
import com.company.utils.ApiController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Log4j2
/*@ApiController(path = "/api/v1/payment")*/
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping
    public ResponseEntity<?> save (@RequestBody @Valid PaymentDTO paymentDTO) {
        log.debug("make account {} ", paymentDTO);
        PaymentDTO response = paymentService.save(paymentDTO);
        return ResponseEntity.ok(new ApiResponse<>("Successfully doing payment", response, HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        log.debug("find payment by id {} ", id);
        return ResponseEntity.ok(new ApiResponse<>("Success founded payment by id", paymentService.findById(id), HttpStatus.OK.value()));
    }
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "minAmount", required = false) Long minAmount,
                                     @RequestParam(value = "maxAmount", required = false) Long maxAmount,
                                     @RequestParam(value = "beginCreatedDate", required = false) String beginCreatedDate,
                                     @RequestParam(value = "endCreatedDate", required = false) String endCreatedDate,
                                     @RequestParam(value = "fromAccount", required = false) Long fromAccount,
                                     @RequestParam(value = "toAccount", required = false) Long toAccount) {
        log.debug("finded all payments");
        return ResponseEntity.ok(new ApiResponse<>("Succcess found all payment", paymentService.findAll
                (minAmount, maxAmount, beginCreatedDate, endCreatedDate, fromAccount, toAccount), HttpStatus.OK.value()));
    }
}
