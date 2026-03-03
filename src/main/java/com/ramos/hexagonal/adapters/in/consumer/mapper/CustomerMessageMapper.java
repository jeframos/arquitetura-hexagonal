package com.ramos.hexagonal.adapters.in.consumer.mapper;

import com.ramos.hexagonal.adapters.in.consumer.message.CustomerMessage;
import com.ramos.hexagonal.application.core.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMessageMapper {

    //Como o "CustomerMessage" não possui o parametro "address", é necessário add essa notação para
    // indicar ao sistema que o obj "Customer" deve aceitar "address" com o valor null.
    @Mapping(target = "address", ignore = true)
    Customer toCustomer(CustomerMessage customerMessage);
}
