package com.example.demo;

import com.example.demo.addressservice.AddressSearchResult;
import com.example.demo.addressservice.AddressSearchResults;
import com.example.demo.customerservice.Customer;
import java.util.List;
import lombok.Value;
import org.springframework.http.ResponseEntity;

@Value
public class CustomerAddressesViewModel {

  private final String name;
  private final List<AddressSearchResult> addresses;

  public CustomerAddressesViewModel(Customer customer, AddressSearchResults addresses) {
    this.name = customer.getName();
    this.addresses = addresses.getAddressSearchResults();
  }


  public CustomerAddressesViewModel(ResponseEntity<Customer> customer, ResponseEntity<AddressSearchResults> addresses) {
    this.name = customer.getBody().getName();
    this.addresses = addresses.getBody().getAddressSearchResults();
  }

}
