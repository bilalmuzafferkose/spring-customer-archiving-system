package com.bilalkose.springcustomerarchivingsystem.repository;

import com.bilalkose.springcustomerarchivingsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByCitizenshipNumber(String citizenShipNumber);

    Customer findByCitizenshipNumber(String citizenshipNumber);
}
