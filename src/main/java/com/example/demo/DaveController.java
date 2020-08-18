package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class DaveController {

  @Autowired
  private RoutePredicateHandlerMapping routePredicateHandlerMapping;
  //
  // @GetMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
  // public Mono<CustomerAddressesViewModel> melia(@Parameter(hidden = true)
  // ProxyExchange<AddressSearchResults> addressProxy, @Parameter(hidden = true)
  // ProxyExchange<Customer> customerProxy) {
  // Mono<AddressSearchResults> response1 = this.proxyAddresses(addressProxy);
  // Mono<Customer> response2 = this.proxyCustomer(customerProxy);
  // return response1.zipWith(response2).map(tuple -> new CustomerAddressesViewModel(tuple.getT2(),
  // tuple.getT1()));
  // }
  //
  // @GetMapping(value = "/proxy-addresses")
  // public Mono<AddressSearchResults> proxyAddresses(@Parameter(hidden = true)
  // ProxyExchange<AddressSearchResults> proxyExchange1) {
  // Mono<ResponseEntity<AddressSearchResults>> response1 =
  // proxyExchange1.uri("http://localhost:8080/api/1/addresses?query=M33NW").get();
  // return response1.map(body -> body.getBody());
  // }
  //
  // @GetMapping(value = "/forward-addresses")
  // public Mono<CustomerAddressesViewModel> proxyAddresseForward(@Parameter(hidden = true)
  // ProxyExchange<AddressSearchResults> addressProxy,
  // @Parameter(hidden = true) ProxyExchange<Customer> customerProxy) {
  // Mono<ResponseEntity<AddressSearchResults>> response1 =
  // addressProxy.uri("http://localhost:8080/proxy-addresses").forward();
  // Mono<ResponseEntity<Customer>> response2 =
  // customerProxy.uri("http://localhost:8080/customer").forward();
  // return response1.zipWith(response2).map(tuple -> new CustomerAddressesViewModel(tuple.getT2(),
  // tuple.getT1()));
  // }
  //
  //
  // @GetMapping(value = "/proxy-customer")
  // public Mono<Customer> proxyCustomer(@Parameter(hidden = true) ProxyExchange<Customer>
  // proxyExchange1) {
  // Mono<ResponseEntity<Customer>> response1 =
  // proxyExchange1.uri("http://localhost:8080/api/1/customers/1234").get();
  // return response1.map(body -> body.getBody());
  // }

  @GetMapping(value = "/forward")
  public Mono<Object> forward(ServerWebExchange exchange) {
    return routePredicateHandlerMapping.getHandler(exchange.mutate().request(exchange.getRequest().mutate().path("/customer").build()).build()).map(r -> {
      System.out.println(r);
      return r;
    });
  }



}
