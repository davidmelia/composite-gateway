package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.example.demo.addressservice.AddressSearchResults;
import com.example.demo.customerservice.Customer;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class RoutePredicateLookupController {
	
	private final RoutePredicateHandlerMapping mapper;

	@GetMapping(value = "/customer2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<CustomerAddressesViewModel> melia( ProxyExchange<AddressSearchResults> addressProxy,ProxyExchange<Customer> customerProxy,ServerWebExchange exchange){
		Mono<AddressSearchResults> response1 = this.proxyAddresses(addressProxy,exchange);
		Mono<Customer> response2 =  this.proxyCustomer(customerProxy);
		return response1.zipWith(response2).map(tuple ->  new CustomerAddressesViewModel(tuple.getT2(), tuple.getT1()));
	}
	
	@GetMapping(value = "/proxy-addresses2")
	public Mono<AddressSearchResults> proxyAddresses(ProxyExchange<AddressSearchResults> proxyExchange1,ServerWebExchange exchange){
		mapper.getHandler(exchange).cast(Route.class).map(route -> route);
		Mono<ResponseEntity<AddressSearchResults>> response1 = proxyExchange1.uri("http://localhost:8080/api/1/addresses?query=M33NW").get();
		return response1.map(body -> body.getBody());
	}
	
	@GetMapping(value = "/proxy-customer2")
	public Mono<Customer> proxyCustomer(ProxyExchange<Customer> proxyExchange1){
	
		
		Mono<ResponseEntity<Customer>> response1 = proxyExchange1.uri("http://localhost:8080/api/1/customers/1234").get();
		return response1.map(body -> body.getBody());
	}
	
}
