package com.example.demo.controller.address.viewmodel;

import com.example.demo.model.addresss.AddressSearchResults;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.http.ResponseEntity;

@Value
@NonFinal
public class AddressSearchResultsViewModel {
  private final List<AddressSearchResultViewModel> addressSearchResults;

  public AddressSearchResultsViewModel() {
    this.addressSearchResults = null;
  }

  public AddressSearchResultsViewModel(AddressSearchResults addressSearchResults) {
    this.addressSearchResults = addressSearchResults.getAddressSearchResults().stream().map(a -> new AddressSearchResultViewModel(a)).collect(Collectors.toList());
  }

  public static ResponseEntity<AddressSearchResultsViewModel> create(ResponseEntity<AddressSearchResults> responseEntity) {
    ResponseEntity<AddressSearchResultsViewModel> response;
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      response = ResponseEntity.ok(new AddressSearchResultsViewModel(responseEntity.getBody()));
    } else {
      response = ResponseEntity.status(responseEntity.getStatusCode()).build();
    }
    return response;

  }

}
