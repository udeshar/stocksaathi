package com.stocksaathi.sales.service.impl;

import com.stocksaathi.sales.dto.CreateCustomerDTO;
import com.stocksaathi.sales.dto.UpdateCustomerDTO;
import com.stocksaathi.sales.exceptions.ResourceAlreadyExistsException;
import com.stocksaathi.sales.exceptions.ResourceNotFoundException;
import com.stocksaathi.sales.mapper.CustomerMapper;
import com.stocksaathi.sales.model.Customer;
import com.stocksaathi.sales.repository.CustomerRepository;
import com.stocksaathi.sales.service.CustomerService;
import com.stocksaathi.sales.specifications.CustomerSpecification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UUID businessId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAllByBusinessIdAndIsDeletedFalse(businessId, pageable);
    }

    @Override
    public Customer findById(UUID id) {
        return customerRepository
                .findByIdAndBusinessIdAndIsDeletedFalse(id, businessId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository
                .findByEmailAndBusinessIdAndIsDeletedFalse(email, businessId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with phone: " + email));
    }

    @Override
    public Customer findByPhone(String phone) {
        return customerRepository
                .findByPhoneAndBusinessIdAndIsDeletedFalse(phone, businessId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with phone: " + phone));
    }

    @Override
    public void deleteById(UUID id) {
        Customer customer = customerRepository
                .findByIdAndBusinessIdAndIsDeletedFalse(id, businessId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));

        customer.setIsDeleted(true);

        customerRepository.save(customer);
    }

    @Override
    public Customer createCustomer(CreateCustomerDTO dto) {
        if(customerRepository.existsByPhoneAndBusinessIdAndIsDeletedFalse(dto.getPhone(), businessId)){
            throw new ResourceAlreadyExistsException("Customer with Phone or Email already exists");
        }
        Customer customer = customerMapper.toEntity(dto);
        customer.setBusinessId(businessId);
        try{
            return customerRepository.save(customer);
        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistsException("Customer with Phone or Email already exists");
        }
    }

    @Override
    public Customer updateCustomer(UUID id, UpdateCustomerDTO dto) {
        Customer customer = customerRepository
                .findByIdAndBusinessIdAndIsDeletedFalse(id, businessId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));

        if (dto.getName() != null) {
            customer.setName(dto.getName());
        }

        if (dto.getAddress() != null) {
            customer.setAddress(dto.getAddress());
        }

        if (dto.getPhone() != null) {
            if (customerRepository.existsByPhoneAndBusinessIdAndIsDeletedFalse(dto.getPhone(), businessId)) {
                throw new ResourceAlreadyExistsException(
                        "Customer with phone " + dto.getPhone() + " already exists");
            }
            customer.setPhone(dto.getPhone());
        }

        if (dto.getEmail() != null) {
            if(customerRepository.existsByEmailAndBusinessIdAndIsDeletedFalse(dto.getEmail(), businessId)) {
                throw new ResourceAlreadyExistsException(
                        "Customer with email " + dto.getEmail() + " already exists"
                );
            }
        }

        try{
            return customerRepository.save(customer);
        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistsException("Duplicate customer data");
        }
    }

    @Override
    public Page<Customer> search(String phone, String email, String name, Pageable pageable) {
        Specification<Customer> spec = CustomerSpecification.filterBy(phone, email, name, businessId);
        return customerRepository.findAll(spec, pageable);
    }
}
