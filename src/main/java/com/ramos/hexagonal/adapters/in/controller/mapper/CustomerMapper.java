package com.ramos.hexagonal.adapters.in.controller.mapper;

import com.ramos.hexagonal.adapters.in.controller.request.CustomerRequest;
import com.ramos.hexagonal.application.core.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    //Essas 3 notações abaixo servem para indicar ao Mapping que esses campos não são enviados pelo DTO "CustomerRequest",
    //então eles devem ser ignorados ao efetuar a conversão para "Customer".
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "isValidCpf", ignore = true)
    Customer toCustomer(CustomerRequest customerRequest);
}
