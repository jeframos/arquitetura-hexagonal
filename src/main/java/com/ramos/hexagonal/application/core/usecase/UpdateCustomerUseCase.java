package com.ramos.hexagonal.application.core.usecase;

import com.ramos.hexagonal.application.core.domain.Customer;
import com.ramos.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import com.ramos.hexagonal.application.ports.in.UpdateCustomerInputPort;
import com.ramos.hexagonal.application.ports.out.FindAddressByZipCodeOutputPort;
import com.ramos.hexagonal.application.ports.out.UpdateCustomerOutputPort;

public class UpdateCustomerUseCase implements UpdateCustomerInputPort {

    private final FindCustomerByIdInputPort findCustomerByIdInputPort;

    private final FindAddressByZipCodeOutputPort findAddressByZipCodeOutPutPort;

    private final UpdateCustomerOutputPort updateCustomerOutputPort;

    public UpdateCustomerUseCase(
            FindCustomerByIdInputPort findCustomerByIdInputPort,
            FindAddressByZipCodeOutputPort findAddressByZipCodeOutPutPort,
            UpdateCustomerOutputPort updateCustomerOutputPort
    ) {
        this.findCustomerByIdInputPort = findCustomerByIdInputPort;
        this.findAddressByZipCodeOutPutPort = findAddressByZipCodeOutPutPort;
        this.updateCustomerOutputPort = updateCustomerOutputPort;
    }

    @Override
    public void update(Customer customer, String zipCode) {
        findCustomerByIdInputPort.find(customer.getId());
        var address = findAddressByZipCodeOutPutPort.find(zipCode);
        customer.setAddress(address);
        updateCustomerOutputPort.update(customer);
    }
}
