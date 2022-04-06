package com.company.model.vm;

import com.company.model.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileJwtVm {
    private int id;
    private ProfileRole role;
}
