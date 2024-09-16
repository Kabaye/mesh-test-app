package com.test.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

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
    private LocalDate dateOfBirth;
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<PhoneData> phones;
    @OneToMany(mappedBy = "user")
    private Set<EmailData> emails;
    @OneToOne(mappedBy = "user")
    private Account account;
}
