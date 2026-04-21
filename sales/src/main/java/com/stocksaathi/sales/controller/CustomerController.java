package com.stocksaathi.sales.controller;

import com.stocksaathi.sales.dto.CreateCustomerDTO;
import com.stocksaathi.sales.dto.UpdateCustomerDTO;
import com.stocksaathi.sales.model.Customer;
import com.stocksaathi.sales.service.CustomerService;
import com.stocksaathi.sales.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<Customer>>> searchCustomers(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {

        if (!sortBy.equals("name") && !sortBy.equals("email")) {
            throw new IllegalArgumentException("Invalid sort field");
        }

        Sort sorting = Sort.by(Sort.Order.by(sortBy).ignoreCase());

        Sort sort = direction.equalsIgnoreCase("desc") ?
                sorting.descending() :
                sorting.ascending();

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        return ResponseEntity.ok(new ApiResponse<>("Fetched data successfully", customerService.search(phone, email, name, sortedPageable)));
    }

//    @GetMapping(params = "phone")
//    public ResponseEntity<Customer> getCustomerByPhone(
//            @RequestParam
//            @NotBlank(message = "Phone is required")
//            @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
//            String phone) {
//        return new ResponseEntity<>(customerService.findByPhone(phone), HttpStatus.OK);
//    }
//
//    @GetMapping(params = "email")
//    public ResponseEntity<Customer> getCustomerByEmail(
//            @RequestParam
//            @NotBlank(message = "Email is required")
//            @Email(message = "Invalid email format")
//            String email) {
//        return new ResponseEntity<>(customerService.findByEmail(email), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@PathVariable UUID id) {
        return new ResponseEntity<>(new ApiResponse<>("Fetched customer successfully", customerService.findById(id)), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<Page<Customer>> getCustomers(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
//        return ResponseEntity.ok(customerService.findAll(pageable));
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCustomerDTO dto) {

        return ResponseEntity.ok(new ApiResponse<>("Updated customer successfully", customerService.updateCustomer(id, dto)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> addCustomer(@Valid @RequestBody CreateCustomerDTO customer) {
        Customer newCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(new ApiResponse<>("Created customer successfully", newCustomer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Customer deleted successfully", null)
        );
    }

}
