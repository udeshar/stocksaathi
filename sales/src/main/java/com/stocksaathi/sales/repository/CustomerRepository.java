package com.stocksaathi.sales.repository;

import com.stocksaathi.sales.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
    boolean existsByPhoneAndBusinessIdAndIsDeletedFalse(String phone, UUID businessId);
    boolean existsByEmailAndBusinessIdAndIsDeletedFalse(String email, UUID businessId);
    Optional<Customer> findByPhoneAndBusinessIdAndIsDeletedFalse(String phone, UUID businessId);
    Optional<Customer> findByIdAndBusinessIdAndIsDeletedFalse(UUID id, UUID businessId);
    Page<Customer> findAllByBusinessIdAndIsDeletedFalse(UUID businessId, Pageable pageable);
    Optional<Customer> findByEmailAndBusinessIdAndIsDeletedFalse(String email, UUID businessId);
}
