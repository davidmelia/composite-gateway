package com.example.demo.controller.composite.viewmodel;

import com.example.demo.controller.address.viewmodel.AddressSearchResultsViewModel;
import com.example.demo.controller.customer.viewmodel.CustomerViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerAddressesViewModelConverter {

  @Autowired
  private ObjectMapper objectMapper;


  public ResponseEntity<CustomerAddressesViewModel2> convert(ResponseEntity<byte[]> customerResponseEntity, ResponseEntity<byte[]> addressesResponseEntity) {
    ResponseEntity<CustomerAddressesViewModel2> result;

    if (!customerResponseEntity.getStatusCode().is2xxSuccessful()) {
      result = ResponseEntity.status(customerResponseEntity.getStatusCode()).build();
    } else if (!addressesResponseEntity.getStatusCode().is2xxSuccessful()) {
      result = ResponseEntity.status(addressesResponseEntity.getStatusCode()).build();
    } else {

      byte[] customer = customerResponseEntity.getBody();
      byte[] addresses = addressesResponseEntity.getBody();
      try {
        CustomerViewModel customerViewModel = objectMapper.readValue(customer, CustomerViewModel.class);
        AddressSearchResultsViewModel addressSearchResultsViewModel = objectMapper.readValue(addresses, AddressSearchResultsViewModel.class);
        result = ResponseEntity.ok(new CustomerAddressesViewModel2(customerViewModel, addressSearchResultsViewModel));
      } catch (Exception e) {
        try {
          log.error("customer response = {}", objectMapper.readValue(customer, String.class));
          log.error("addresses response = {}", objectMapper.readValue(addresses, String.class));
        } catch (Exception e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        throw new RuntimeException(e);
      }
    }
    return result;
  }

}
