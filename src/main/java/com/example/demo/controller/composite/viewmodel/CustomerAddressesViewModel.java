package com.example.demo.controller.composite.viewmodel;

import com.example.demo.model.addresss.AddressSearchResult;
import com.example.demo.model.addresss.AddressSearchResults;
import com.example.demo.model.customer.Customer;
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

  public CustomerAddressesViewModel(ResponseEntity<Customer> customer) {
    this.name = customer.getBody().getName();
    this.addresses = null;
  }
}
