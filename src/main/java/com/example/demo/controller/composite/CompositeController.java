package com.example.demo.controller.composite;

import com.example.demo.controller.composite.viewmodel.CustomerAddressesViewModel2;
import com.example.demo.controller.composite.viewmodel.CustomerAddressesViewModelConverter;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private CustomerAddressesViewModelConverter converter;

  @GetMapping(value = "/composite/{customerId}")
  public Mono<ResponseEntity<CustomerAddressesViewModel2>> proxyAddresses2(@Parameter(hidden = true) ProxyExchange<byte[]> customerServiceProxy,
      @Parameter(hidden = true) ProxyExchange<byte[]> addressServiceProxy, @PathVariable String customerId, @RequestParam("query") String postcode, UriComponentsBuilder builder) {
    Mono<ResponseEntity<byte[]>> customer = customerServiceProxy.uri(builder.cloneBuilder().path("/services/api/1/customers/{customerId}").buildAndExpand(customerId).toUriString()).get();
    Mono<ResponseEntity<byte[]>> addresses = addressServiceProxy.uri(builder.cloneBuilder().path("/services/api/1/addresses").queryParam("query", postcode).build().toUriString()).get();
    return customer.zipWith(addresses).map(tuple -> converter.convert(tuple.getT1(), tuple.getT2()));
  }

}
