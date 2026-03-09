package com.ramos.hexagonal.application.ports.out;

import com.ramos.hexagonal.application.core.domain.Address;

public interface FindAddressByZipCodeOutputPort {

    Address find(String ZipCode);
}
