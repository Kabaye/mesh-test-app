package com.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "user_table")
public class User {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<PhoneData> phones;
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<EmailData> emails;
    @JsonManagedReference
    @OneToOne(mappedBy = "user")
    private Account account;
}
