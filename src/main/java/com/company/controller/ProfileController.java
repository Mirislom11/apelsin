package com.company.controller;

import com.company.model.dto.ProfileDTO;
import com.company.model.response.ApiResponse;
import com.company.model.vm.ProfileJwtVm;
import com.company.service.impl.ProfileServiceImpl;
import com.company.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileServiceImpl profileServiceImpl;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ProfileDTO profileDTO) {
        log.debug("Profile saving");
        ApiResponse<ProfileDTO> response = new ApiResponse<>
                ("Successfully saved", profileServiceImpl.save(profileDTO), HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") @Valid @Positive long id) {
        log.debug("find profile by id{}", id);
        ApiResponse<ProfileDTO> response = new ApiResponse<>("Successfully saved", profileServiceImpl.findById(id),
                HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "name", required = false) String name,
                                     @RequestParam(name = "lastName", required = false) String lastName, @RequestParam(name = "login", required = false)
                                                String login) {
        log.debug("Find all");
        ApiResponse<List<ProfileDTO>> listApiResponse = new ApiResponse<>("Successfully find", profileServiceImpl.findAll(name, lastName,login), HttpStatus.OK.value());
        return ResponseEntity.ok(listApiResponse);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProfileDTO profileDTO, HttpServletRequest request) {
        log.debug("Updating profile{}", profileDTO);
        ProfileJwtVm profileJwtVm = JwtUtils.getProfile(request);
        ApiResponse<ProfileDTO> apiResponse = new ApiResponse<>("Successfully update", profileServiceImpl.updateById(profileJwtVm.getId(), profileDTO), HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(HttpServletRequest request) {
        ProfileJwtVm profileJwtVm = JwtUtils.getProfile(request);
        log.debug("Deleting profile id{}", profileJwtVm.getId());
        return ResponseEntity.ok(profileServiceImpl.deleteById(profileJwtVm.getId()));

    }
}
