package com.test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
@Entity
@Accessors(chain = true)
public class Account {
    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "max_balance")
    private BigDecimal maxBalance;
}
