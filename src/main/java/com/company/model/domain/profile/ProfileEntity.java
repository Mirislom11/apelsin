package com.company.model.domain.profile;

import com.company.model.domain.BaseEntity;
import com.company.model.dto.ProfileDTO;
import com.company.model.enums.ProfileRole;
import com.company.model.enums.ProfileStatus;
import com.company.utils.DateTimeUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {
    @Column(name = "name", length = 32)
    private String name;
    @Column(name = "last_name", length = 32)
    private String lastName;
    @Column(name = "login", unique = true, nullable = false, length = 32)
    private String login;
    @Column(name = "email", unique = true, nullable = false, length = 32)
    private String email;
    @Column(name = "password", nullable = false, length = 12)
    private String password;
    @Column(name = "profile_status", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Column(name = "profile_role", nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
    public ProfileDTO map2DTO() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(super.getId());
        profileDTO.setName(this.getName());
        profileDTO.setLogin(this.getLogin());
        profileDTO.setEmail(this.getEmail());
        profileDTO.setId(this.getId());
        profileDTO.setSurname(this.getLastName());
        profileDTO.setPassword(this.getPassword());
        profileDTO.setRole(this.getRole());
        profileDTO.setStatus(this.getStatus());
        profileDTO.setCreatedDateTime(DateTimeUtil.LocalDateTimeToString(this.getCreatedDateTime()));
        return profileDTO;
    }
    public ProfileHistoryEntity map2ProfileHistory() {
        ProfileHistoryEntity profileHistoryEntity = new ProfileHistoryEntity();
        profileHistoryEntity.setId(this.getId());
        profileHistoryEntity.setName(this.getName());
        profileHistoryEntity.setLastName(this.getLastName());
        profileHistoryEntity.setLogin(this.getLogin());
        profileHistoryEntity.setEmail(this.getEmail());
        profileHistoryEntity.setPassword(this.getPassword());
        profileHistoryEntity.setStatus(this.getStatus());
        profileHistoryEntity.setLastModifiedDate(this.getLastModifiedDate());
        profileHistoryEntity.setVersion(this.getVersion());
        profileHistoryEntity.setCreatedDateTime(this.getCreatedDateTime());
        return profileHistoryEntity;
    }
}
