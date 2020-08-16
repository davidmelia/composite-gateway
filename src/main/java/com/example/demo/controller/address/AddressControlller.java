package com.example.demo.controller.address;

import com.example.demo.controller.address.viewmodel.AddressSearchResultsViewModel;
import com.example.demo.model.addresss.AddressSearchResults;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class AddressControlller {

  @GetMapping(value = "/addresses")
  public Mono<AddressSearchResultsViewModel> proxyAddresses(@Parameter(hidden = true) ProxyExchange<AddressSearchResults> addressServiceProxy, @RequestParam("query") String postcode,
      UriComponentsBuilder builder) {
    return addressServiceProxy.uri(builder.path("/api/1/addresses").queryParam("query", postcode).build().toUriString()).get()
        .map(addressSearchResults -> new AddressSearchResultsViewModel(addressSearchResults.getBody()));
  }

}
