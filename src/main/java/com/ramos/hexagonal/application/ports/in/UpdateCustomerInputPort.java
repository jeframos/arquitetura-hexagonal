package com.ramos.hexagonal.application.ports.in;

import com.ramos.hexagonal.application.core.domain.Customer;

public interface UpdateCustomerInputPort {

    void update(Customer customer, String zipCode);
}
