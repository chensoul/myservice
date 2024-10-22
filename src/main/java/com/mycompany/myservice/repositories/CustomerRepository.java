package com.mycompany.myservice.repositories;

import com.mycompany.myservice.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    Page<Customer> findAll(Pageable pageable);
    ;
}
