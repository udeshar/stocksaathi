package com.stocksaathi.sales.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ErrorResponse {
    private Integer status;
    private String errorCode;
    private String message;
    private List<FieldErrors> errors;

    public ErrorResponse(Integer status, String errorCode, String message, List<FieldErrors> errors) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

}
