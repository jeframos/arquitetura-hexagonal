package com.ramos.hexagonal.application.ports.out;

import com.ramos.hexagonal.application.core.domain.Address;

public interface FindAddressByZipCodeOutPutPort {

    Address find(String ZipCode);
}
