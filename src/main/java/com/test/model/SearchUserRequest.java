package com.test.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class SearchUserRequest {
    private String phone;
    private String email;
    private String name;
    private LocalDate date;

    private Integer size;
    private Integer page;
}
