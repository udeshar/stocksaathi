package com.stocksaathi.sales.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private String message = "Operation successful";
    private T data;
    private Boolean errors = false;

    public ApiResponse() {
    }

    public ApiResponse(String message,  T data, Boolean error) {
        this.message = message;
        this.data = data;
        this.errors = error;
    }

    public ApiResponse(String message,  T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(T data) {
        this.data = data;
    }
}