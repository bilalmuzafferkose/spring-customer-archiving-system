package com.bilalkose.springcustomerarchivingsystem.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpdateCustomerRequest {
    @NotNull
    @Size(min = 2, max = 30, message = "Name must be between at 2 and 30 characters long")
    private String name;
    @NotNull
    @Size(min = 2, max = 11, message = "Surname must be between at 2 and 30 characters long")
    private String surname;
    @Column(unique = true)
    @Email
    private String email;
    private LocalDate birthDay;

}
