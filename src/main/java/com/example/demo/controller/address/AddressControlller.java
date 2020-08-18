package com.example.demo.controller.address;

import io.swagger.v3.oas.annotations.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class AddressControlller {

  @GetMapping(value = "/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<byte[]>> addresses(@Parameter(hidden = true) ProxyExchange<byte[]> addressServiceProxy, @RequestParam("query") String postcode, UriComponentsBuilder builder) {
    return addressServiceProxy.uri(builder.path("/services/api/1/addresses").queryParam("query", postcode).build().toUriString()).get();
  }

  @GetMapping(value = "/direct/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<byte[]>> directProxyAddresses(@Parameter(hidden = true) ProxyExchange<byte[]> addressServiceProxy, @RequestParam("query") String postcode, UriComponentsBuilder builder)
      throws URISyntaxException {
    return addressServiceProxy.uri(builder.uri(new URI("http://localhost:9090/address-lookup-service/api/1/addresses")).queryParam("query", postcode).build().toUriString()).get();
  }


}
