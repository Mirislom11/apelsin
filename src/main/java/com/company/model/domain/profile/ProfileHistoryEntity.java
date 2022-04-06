package com.company.model.domain.profile;

import com.company.model.domain.BaseEntity;
import com.company.model.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "profile_history")
public class ProfileHistoryEntity extends BaseEntity{
    @Column(name = "name", length = 32)
    private String name;
    @Column(name = "last_name", length = 32)
    private String lastName;
    @Column(name = "login",  nullable = false, length = 32)
    private String login;
    @Column(name = "email",  nullable = false, length = 32)
    private String email;
    @Column(name = "password", nullable = false, length = 12)
    private String password;
    @Column(name = "profile_status", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
}
