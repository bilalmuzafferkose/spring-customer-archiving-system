package com.bilalkose.springcustomerarchivingsystem.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CustomerDto {
    private String citizenshipNumber;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDay;
    private List<FileDto> files;
}
