package com.company.model.dto;

import com.company.model.domain.profile.ProfileEntity;
import com.company.model.enums.ProfileRole;
import com.company.model.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDTO {
    private long id;
    @NotEmpty(message = "name can not be empty or null")
    private String name;
    @NotEmpty(message = "surname can not be empty or null")
    private String surname;
    @NotBlank(message = "login can not be null or has trim")
    @Size(min = 4, max = 8, message = "login length min 4 and max 8")
    private String login;
    @Size(min = 4, max = 12, message = "password length must be min 4 max 12")
    @NotBlank(message = "password can not be null or has trim")
    private String password;
    @Email(message = "email no correct")
    private String email;
    private ProfileStatus status;
    private ProfileRole role;
    private String createdDateTime;

    public ProfileEntity map2Entity() {
        ProfileEntity profile = new ProfileEntity();
        profile.setEmail(this.getEmail());
        profile.setLogin(this.getLogin());
        profile.setName(this.getName());
        profile.setLastName(this.getSurname());
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setRole(this.getRole());
        profile.setPassword(this.getPassword());
        profile.setLastModifiedDate(profile.getCreatedDateTime());
        profile.setVersion(1l);
        return profile;
    }
}
