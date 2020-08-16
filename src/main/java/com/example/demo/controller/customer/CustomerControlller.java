package com.example.demo.controller.customer;

import com.example.demo.controller.customer.viewmodel.CustomerViewModel;
import com.example.demo.model.customer.Customer;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class CustomerControlller {

  @GetMapping(value = "/customer/{customerId}")
  public Mono<CustomerViewModel> proxyAddresses(@Parameter(hidden = true) ProxyExchange<Customer> customerServiceProxy, @PathVariable String customerId, UriComponentsBuilder builder) {
    return customerServiceProxy.uri(builder.path("api/1/customers/{customerId}").buildAndExpand(customerId).toUriString()).get().map(customer -> new CustomerViewModel(customer.getBody()));
  }

}
