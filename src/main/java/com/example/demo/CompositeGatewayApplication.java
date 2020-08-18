package com.example.demo;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

import com.example.demo.controller.address.viewmodel.AddressSearchResultsViewModel;
import com.example.demo.controller.customer.viewmodel.CustomerViewModel;
import com.example.demo.model.addresss.AddressSearchResults;
import com.example.demo.model.customer.Customer;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@SpringBootApplication
@AutoConfigureWireMock
public class CompositeGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(CompositeGatewayApplication.class, args);
  }

  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    Set<String> httpStatusesToCircuitBreak = Arrays.stream(HttpStatus.values()).filter(status -> status.is5xxServerError() || status.is4xxClientError())
        .filter(status -> status != HttpStatus.NOT_FOUND).filter(status -> status != HttpStatus.BAD_REQUEST).map(status -> status.name()).collect(Collectors.toSet());

    return builder.routes()
        .route(p -> p.path("/services/api/1/addresses")
            .filters(f -> f.circuitBreaker(c -> c.setName("address-lookup-service").setStatusCodes(httpStatusesToCircuitBreak))
                .rewritePath("/services/api/1/addresses", "/address-lookup-service/api/1/addresses")
                .modifyResponseBody(AddressSearchResults.class, AddressSearchResultsViewModel.class, (t, u) -> Mono.just(new AddressSearchResultsViewModel(u))))
            .uri("http://localhost:9090").metadata(RESPONSE_TIMEOUT_ATTR, 15000).metadata(CONNECT_TIMEOUT_ATTR, 5000))
        .route(p -> p.path("/services/api/1/customers/*").filters(f -> f.circuitBreaker(c -> c.setName("customer-service").setStatusCodes(httpStatusesToCircuitBreak))
            .rewritePath("/services/api/1/customers/", "/customer-service/api/1/customers/").modifyResponseBody(Customer.class, CustomerViewModel.class, (t, u) -> Mono.just(new CustomerViewModel(u))))
            .uri("http://localhost:9090").metadata(RESPONSE_TIMEOUT_ATTR, 15000).metadata(CONNECT_TIMEOUT_ATTR, 5000))
        .build();
  }
}
