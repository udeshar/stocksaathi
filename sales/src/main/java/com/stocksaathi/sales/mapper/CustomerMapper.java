package com.stocksaathi.sales.mapper;

import com.stocksaathi.sales.dto.CreateCustomerDTO;
import com.stocksaathi.sales.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CreateCustomerDTO dto);
}
