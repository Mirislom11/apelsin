package com.company.service;


import com.company.model.dto.BankDTO;
import com.company.model.dto.ProfileDTO;

import java.util.List;

public interface BankService {
    BankDTO save(BankDTO bankDTO);
    BankDTO findById(long id);
    List<BankDTO> findAll(String name, String phone);

    BankDTO update(long id, BankDTO bankDTO);

    void delete(long id);
}
