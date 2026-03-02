package com.ramos.hexagonal.application.ports.in;

import com.ramos.hexagonal.application.core.domain.Customer;

public interface InsertCustomerInputPort {

    void insert(Customer customer, String zipCode);
}
