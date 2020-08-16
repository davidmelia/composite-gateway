package com.example.demo.controller.composite;

import com.example.demo.controller.composite.viewmodel.CustomerAddressesViewModel;
import com.example.demo.model.addresss.AddressSearchResults;
import com.example.demo.model.customer.Customer;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class CompositeController {

  @GetMapping(value = "/customer-addresses/{customerId}")
  public Mono<CustomerAddressesViewModel> proxyAddresses(@Parameter(hidden = true) ProxyExchange<Customer> customerServiceProxy,
      @Parameter(hidden = true) ProxyExchange<AddressSearchResults> addressServiceProxy, @PathVariable String customerId, @RequestParam("query") String postcode, UriComponentsBuilder builder) {
    Mono<ResponseEntity<AddressSearchResults>> addressSearchResults = addressServiceProxy.uri(builder.path("/api/1/addresses").queryParam("query", postcode).build().toUriString()).get();
    Mono<ResponseEntity<Customer>> customer = customerServiceProxy.uri(builder.path("api/1/customers/{customerId}").buildAndExpand(customerId).toUriString()).get();
    return addressSearchResults.zipWith(customer).map(tuple -> new CustomerAddressesViewModel(tuple.getT2(), tuple.getT1()));
  }


}
