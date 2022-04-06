package com.company.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String message;
    private T data;
    private int code;

    public ApiResponse(T data, int code) {
        this.data = data;
        this.code = code;
    }

    public ApiResponse(String message, T data, int code){
        this.message = message;
        this.data = data;
        this.code = code;
    }
    public ApiResponse(String message, int code){
        this.message = message;
        this.code = code;
    }
}
