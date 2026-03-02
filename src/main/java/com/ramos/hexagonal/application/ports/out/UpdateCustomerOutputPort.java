package com.ramos.hexagonal.application.ports.out;

import com.ramos.hexagonal.application.core.domain.Customer;

public interface UpdateCustomerOutputPort {

    void update(Customer customer);
}
