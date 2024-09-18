package com.test.security.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationResponse {
    private String token;
    private String type = "Bearer";
}
