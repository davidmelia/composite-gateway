package com.example.demo.controller.customer;

import io.swagger.v3.oas.annotations.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class CustomerControlller {

  @GetMapping(value = "/customers/{customerId}")
  public Mono<ResponseEntity<byte[]>> customers(@Parameter(hidden = true) ProxyExchange<byte[]> customerServiceProxy, @PathVariable String customerId, UriComponentsBuilder builder) {
    return customerServiceProxy.uri(builder.path("/services/api/1/customers/{customerId}").buildAndExpand(customerId).toUriString()).get();
  }

  @GetMapping(value = "/direct/customers/{customerId}")
  public Mono<ResponseEntity<byte[]>> directCustomers(@Parameter(hidden = true) ProxyExchange<byte[]> customerServiceProxy, @PathVariable String customerId, UriComponentsBuilder builder)
      throws URISyntaxException {
    return customerServiceProxy.uri(builder.uri(new URI("http://localhost:9090/customer-service/api/1/customers/" + customerId)).toUriString()).get();
  }

}
