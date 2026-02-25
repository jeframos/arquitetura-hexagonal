package com.ramos.hexagonal.adapters.out.repository.mapper;

import com.ramos.hexagonal.adapters.out.repository.entity.CustomerEntity;
import com.ramos.hexagonal.application.core.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    CustomerEntity toCustomerEntity(Customer customer);
}
