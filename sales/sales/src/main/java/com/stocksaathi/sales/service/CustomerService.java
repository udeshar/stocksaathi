package com.stocksaathi.sales.service;

import com.stocksaathi.sales.dto.CreateCustomerDTO;
import com.stocksaathi.sales.dto.UpdateCustomerDTO;
import com.stocksaathi.sales.model.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    public Page<Customer> findAll(Pageable pageable);
    public Customer findById(UUID id);
    public Customer findByEmail(String email);
    public Customer findByPhone(String phone);

    public void deleteById(UUID id);
    public Customer createCustomer(CreateCustomerDTO customer);

    Customer updateCustomer(UUID id, @Valid UpdateCustomerDTO dto);

    Page<Customer> search(String phone, String email, String name, Pageable pageable);
}
