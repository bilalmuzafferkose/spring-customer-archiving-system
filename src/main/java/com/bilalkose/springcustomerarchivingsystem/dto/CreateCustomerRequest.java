package com.bilalkose.springcustomerarchivingsystem.dto;

import com.bilalkose.springcustomerarchivingsystem.model.File;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CreateCustomerRequest {
    @NotNull
    @Size(min = 11, max = 11, message = "Citizenship number must be exactly 11 characters long")
    @Column(unique = true)
    private String citizenshipNumber;
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
    private List<File> files;
}
