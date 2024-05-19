package com.bilalkose.springcustomerarchivingsystem.dto.converter;

import com.bilalkose.springcustomerarchivingsystem.dto.CreateCustomerRequest;
import com.bilalkose.springcustomerarchivingsystem.dto.CustomerDto;
import com.bilalkose.springcustomerarchivingsystem.dto.FileDto;
import com.bilalkose.springcustomerarchivingsystem.dto.UpdateCustomerRequest;
import com.bilalkose.springcustomerarchivingsystem.model.Customer;
import com.bilalkose.springcustomerarchivingsystem.model.File;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {
    public CustomerDto convert(Customer customer) {
        return CustomerDto.builder()
                .citizenshipNumber(customer.getCitizenshipNumber())
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .birthDay(customer.getBirthDay())
                .files(convertFilesToFileDtos(customer.getFiles()))
                .build();
    }

    public Customer convert(CustomerDto customerDto) {
        return Customer.builder()
                .citizenshipNumber(customerDto.getCitizenshipNumber())
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .email(customerDto.getEmail())
                .birthDay(customerDto.getBirthDay())
                .files(convertFileDtosToFiles(customerDto.getFiles()))
                .build();
    }

    //update
    public Customer convert(UpdateCustomerRequest updateCustomerRequest) {
        return Customer.builder()
                .name(updateCustomerRequest.getName())
                .surname(updateCustomerRequest.getSurname())
                .email(updateCustomerRequest.getEmail())
                .birthDay(updateCustomerRequest.getBirthDay())
                .build();
    }

    public Customer convert(CreateCustomerRequest createCustomerRequest) {
        return Customer.builder()
                .name(createCustomerRequest.getName())
                .surname(createCustomerRequest.getSurname())
                .citizenshipNumber(createCustomerRequest.getCitizenshipNumber())
                .email(createCustomerRequest.getEmail())
                .birthDay(createCustomerRequest.getBirthDay())
                .files(new ArrayList<>())
                .build();
    }

    private List<FileDto> convertFilesToFileDtos(List<File> files) {
        return files.stream()
                .map(this::convertFileToFileDto)
                .collect(Collectors.toList());
    }

    private FileDto convertFileToFileDto(File file) {
        return new FileDto(file.getName(), file.getUrl());
    }

    private List<File> convertFileDtosToFiles(List<FileDto> fileDtos) {
        return fileDtos.stream()
                .map(this::convertFileDtoToFile)
                .collect(Collectors.toList());
    }

    private File convertFileDtoToFile(FileDto fileDto) {
        File file = new File();
        file.setName(fileDto.getName());
        file.setUrl(fileDto.getUrl());
        return file;
    }

}
