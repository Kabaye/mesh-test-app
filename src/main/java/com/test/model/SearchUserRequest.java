package com.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class SearchUserRequest {
    private String phone;
    private String email;
    private String name;
    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    private Integer size;
    private Integer page;
}
