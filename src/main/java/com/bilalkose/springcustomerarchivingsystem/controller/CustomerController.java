package com.bilalkose.springcustomerarchivingsystem.controller;

import com.bilalkose.springcustomerarchivingsystem.dto.CreateCustomerRequest;
import com.bilalkose.springcustomerarchivingsystem.dto.CustomerDto;
import com.bilalkose.springcustomerarchivingsystem.dto.UpdateCustomerRequest;
import com.bilalkose.springcustomerarchivingsystem.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/customer")
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerDtoById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
       return ResponseEntity.ok( customerService.save(createCustomerRequest));
    }

    @PutMapping("/update/{citizenshipNumber}")
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @PathVariable String citizenshipNumber, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return ResponseEntity.ok(customerService.updateCustomer(citizenshipNumber, updateCustomerRequest));
    }

    @DeleteMapping("/delete/{citizenshipNumber}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String citizenshipNumber) {
        customerService.deleteCustomer(citizenshipNumber);
        return ResponseEntity.ok("Customer with citizenshipNumber " + citizenshipNumber + " is deleted.");
    }


}
