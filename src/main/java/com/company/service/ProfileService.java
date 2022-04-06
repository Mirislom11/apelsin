package com.company.service;

import com.company.model.dto.ProfileDTO;
import com.company.model.response.ApiResponse;

import java.util.List;

public interface ProfileService {
    ProfileDTO save (ProfileDTO profileDTO);
    ProfileDTO findById(long id);
    List<ProfileDTO> findAll(String name, String surname, String login);
    ProfileDTO updateById(long id, ProfileDTO profileDTO);
    ProfileDTO deleteById(long id);
}
