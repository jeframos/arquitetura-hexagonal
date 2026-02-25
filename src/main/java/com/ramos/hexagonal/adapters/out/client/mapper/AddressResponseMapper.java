package com.ramos.hexagonal.adapters.out.client.mapper;

import com.ramos.hexagonal.adapters.out.client.response.AddressResponse;
import com.ramos.hexagonal.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {

    Address toAddress(AddressResponse addressResponse);
}
