package com.bilalkose.springcustomerarchivingsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntityAudit {
    private String name;
    private String surname;
    @Column(unique = true)
    private String citizenshipNumber;
    @Column(unique = true)
    @Email
    private String email;
    private LocalDate birthDay;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files;
}
