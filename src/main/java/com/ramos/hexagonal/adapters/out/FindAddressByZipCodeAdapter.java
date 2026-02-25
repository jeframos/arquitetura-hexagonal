package com.ramos.hexagonal.adapters.out;

import com.ramos.hexagonal.adapters.out.client.FindAddressByZipCodeClient;
import com.ramos.hexagonal.adapters.out.client.mapper.AddressResponseMapper;
import com.ramos.hexagonal.application.core.domain.Address;
import com.ramos.hexagonal.application.ports.out.FindAddressByZipCodeOutPutPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Como não estamos dentro do "core" podemos utilizar notações.
//Neste caso estamos usando "@Component" para gerenciar as dependencias da classe.
@Component
public class FindAddressByZipCodeAdapter implements FindAddressByZipCodeOutPutPort {

    @Autowired
    private FindAddressByZipCodeClient findAddressByZipCodeClient;

    @Autowired
    private AddressResponseMapper addressResponseMapper;

    @Override
    public Address find(String zipCode) {
        var addressResponse = findAddressByZipCodeClient.find(zipCode);
        return addressResponseMapper.toAddress(addressResponse);
    }
}
