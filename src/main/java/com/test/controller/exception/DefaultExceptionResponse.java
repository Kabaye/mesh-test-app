package com.test.controller.exception;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DefaultExceptionResponse {
    private String message;
    private String cause;
}
