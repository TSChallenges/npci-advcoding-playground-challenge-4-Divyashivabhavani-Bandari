package com.bankmgmt.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String accountHolderName;

    @Positive
    private double balance = 0.0;

    @NotNull
    @Pattern(regexp = "SAVINGS|CURRENT", message = "Account type must be either SAVINGS or CURRENT")
    private String accountType;

    @NotNull
    @Email
    private String email;
}
