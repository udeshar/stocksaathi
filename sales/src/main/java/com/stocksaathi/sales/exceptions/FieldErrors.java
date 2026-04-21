package com.stocksaathi.sales.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldErrors {
    private String field;
    private String message;

    public FieldErrors(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
