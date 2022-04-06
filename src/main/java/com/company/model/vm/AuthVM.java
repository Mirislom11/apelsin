package com.company.model.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthVM {
    @NotEmpty(message = "login should not be null or empty")
    private String login;
    @NotEmpty(message = "password should not be null or empty")
    private String password;
    private String jwt;
}
