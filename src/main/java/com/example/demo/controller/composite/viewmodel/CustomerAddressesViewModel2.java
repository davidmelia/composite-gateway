package com.example.demo.controller.composite.viewmodel;

import com.example.demo.controller.address.viewmodel.AddressSearchResultsViewModel;
import com.example.demo.controller.customer.viewmodel.CustomerViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;

@Value
public class CustomerAddressesViewModel2 {

  private final String name;
  private final AddressSearchResultsViewModel addresses;

  public CustomerAddressesViewModel2(ObjectMapper om, byte[] customer, byte[] addresses) {
    CustomerViewModel customerViewModel;
    AddressSearchResultsViewModel addressSearchResultsViewModel;
    System.out.println(new String(customer));
    System.out.println(new String(addresses));
    try {
      customerViewModel = om.readValue(customer, CustomerViewModel.class);
      addressSearchResultsViewModel = om.readValue(addresses, AddressSearchResultsViewModel.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    this.name = customerViewModel.getName();
    this.addresses = addressSearchResultsViewModel;
  }

  public CustomerAddressesViewModel2(CustomerViewModel customerViewModel, AddressSearchResultsViewModel addressSearchResultsViewModel) {
    this.name = customerViewModel.getName();
    this.addresses = addressSearchResultsViewModel;
  }

}
