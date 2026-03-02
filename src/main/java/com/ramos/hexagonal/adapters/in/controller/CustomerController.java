package com.ramos.hexagonal.adapters.in.controller;

import com.ramos.hexagonal.adapters.in.controller.mapper.CustomerMapper;
import com.ramos.hexagonal.adapters.in.controller.request.CustomerRequest;
import com.ramos.hexagonal.adapters.in.controller.response.CustomerResponse;
import com.ramos.hexagonal.application.core.domain.Customer;
import com.ramos.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import com.ramos.hexagonal.application.ports.in.InsertCustomerInputPort;
import com.ramos.hexagonal.application.ports.in.UpdateCustomerInputPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private InsertCustomerInputPort insertCustomerInputPort;

    @Autowired
    private FindCustomerByIdInputPort findCustomerByIdInputPort;

    @Autowired
    private UpdateCustomerInputPort updateCustomerInputPort;

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CustomerRequest customerRequest) {
        var customer = customerMapper.toCustomer(customerRequest);

        //Como podemos notar o segundo parametro do "insert()" deve ser o zipCode,
        //porém como "Customer" não foi configurado com zipCode, é fornecido nesse segundo parametro "customerRequest.getZipCode()".
        //Para o sistema conseguir até o serviço de busca de endereço.
        insertCustomerInputPort.insert(customer, customerRequest.getZipCode());
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable final String id) {
        var customer = findCustomerByIdInputPort.find(id);
        var customerResponse = customerMapper.toCustomerResponse(customer);
        return ResponseEntity.ok().body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final String id,
                                       @Valid @RequestBody CustomerRequest customerRequest) {
        Customer customer = customerMapper.toCustomer(customerRequest);
        //Como o "customerRequest" não possui o ID, então é necessário efetuar o ".setId(id)" abaixo
        // para conseguir localizar o customer, e efetuar a atualização dos dados.
        customer.setId(id);
        updateCustomerInputPort.update(customer, customerRequest.getZipCode());
        return ResponseEntity.noContent().build();
    }
}
