package com.bilalkose.springcustomerarchivingsystem.service;

import com.bilalkose.springcustomerarchivingsystem.dto.CreateCustomerRequest;
import com.bilalkose.springcustomerarchivingsystem.dto.CustomerDto;
import com.bilalkose.springcustomerarchivingsystem.dto.UpdateCustomerRequest;
import com.bilalkose.springcustomerarchivingsystem.dto.converter.CustomerDtoConverter;
import com.bilalkose.springcustomerarchivingsystem.exception.AlreadyCreatedCustomerException;
import com.bilalkose.springcustomerarchivingsystem.exception.CustomerNotFoundException;
import com.bilalkose.springcustomerarchivingsystem.model.Customer;
import com.bilalkose.springcustomerarchivingsystem.producer.RabbitMQProducer;
import com.bilalkose.springcustomerarchivingsystem.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;
    private final RabbitMQProducer rabbitMQProducer;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter, RabbitMQProducer rabbitMQProducer) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerDtoById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer could not find by id: " + id)
                );
        return this.customerDtoConverter.convert(customer);
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer could not find by id: " + id)
                );
    }

    public CustomerDto save(CreateCustomerRequest createCustomerRequest) {
        this.checkIfCustomerIsExistsByCitizenshipNumber(createCustomerRequest.getCitizenshipNumber());
        Customer customer = customerDtoConverter.convert(createCustomerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer Saved: " + createCustomerRequest.getCitizenshipNumber() + " | "
                + createCustomerRequest.getName() + " | " + createCustomerRequest.getSurname() + " | " + createCustomerRequest.getEmail());
        rabbitMQProducer.sendWelcomeEmailToQueue(savedCustomer.getEmail());
        return this.customerDtoConverter.convert(savedCustomer);
    }

    public CustomerDto updateCustomer(String citizenshipNumber, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findByCitizenshipNumber(citizenshipNumber);

        customer.setName(updateCustomerRequest.getName());
        customer.setSurname(updateCustomerRequest.getSurname());
        customer.setBirthDay(updateCustomerRequest.getBirthDay());

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer Updated: " + updateCustomerRequest.getName() + " | " + updateCustomerRequest.getSurname() + " | " + updateCustomerRequest.getEmail());
        return customerDtoConverter.convert(savedCustomer);
    }

    public void deleteCustomer(String citizenshipNumber) {
        this.checkIfCustomerIsNotExistsWithCitizenshipNumber(citizenshipNumber);
        Customer customer = customerRepository.findByCitizenshipNumber(citizenshipNumber);
        this.customerRepository.deleteById(customer.getId());
        log.info("Customer Deleted: " + customer.getCitizenshipNumber() + " | "
                + customer.getName() + " | " + customer.getSurname());
    }

    private void checkIfCustomerIsNotExistsWithCitizenshipNumber(String citizenshipNumber) {
        if (!this.customerRepository.existsByCitizenshipNumber(citizenshipNumber)) {
            throw new CustomerNotFoundException("There is no customer for this citizenshipNumber : " + citizenshipNumber);
        }
    }

    private void checkIfCustomerIsExistsByCitizenshipNumber(String citizenshipNumber) {
        if (this.customerRepository.existsByCitizenshipNumber(citizenshipNumber)) {
            throw new AlreadyCreatedCustomerException("Already created for this citizenshipNumber : " + citizenshipNumber);
        }
    }
}
