package com.ramos.hexagonal.application.core.usecase;

import com.ramos.hexagonal.application.core.domain.Customer;
import com.ramos.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import com.ramos.hexagonal.application.ports.out.FindAddressByZipCodeOutPutPort;
import com.ramos.hexagonal.application.ports.out.UpdateCustomerOutputPort;

public class UpdateCustomerByIdUseCase {

    private final FindCustomerByIdInputPort findCustomerByIdInputPort;

    private final FindAddressByZipCodeOutPutPort findAddressByZipCodeOutPutPort;

    private final UpdateCustomerOutputPort updateCustomerOutputPort;

    public UpdateCustomerByIdUseCase(
            FindCustomerByIdInputPort findCustomerByIdInputPort,
            FindAddressByZipCodeOutPutPort findAddressByZipCodeOutPutPort,
            UpdateCustomerOutputPort updateCustomerOutputPort
    ) {
        this.findCustomerByIdInputPort = findCustomerByIdInputPort;
        this.findAddressByZipCodeOutPutPort = findAddressByZipCodeOutPutPort;
        this.updateCustomerOutputPort = updateCustomerOutputPort;
    }

    public void update(Customer customer, String zipCode) {
        findCustomerByIdInputPort.find(customer.getId());
        var address = findAddressByZipCodeOutPutPort.find(zipCode);
        customer.setAddress(address);
        updateCustomerOutputPort.update(customer);
    }
}
