package com.example.demo.controller.address.viewmodel;

import com.example.demo.model.addresss.AddressSearchResults;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class AddressSearchResultsViewModel {
  private final List<AddressSearchResultViewModel> addressSearchResults;

  public AddressSearchResultsViewModel(AddressSearchResults addressSearchResults) {
    this.addressSearchResults = addressSearchResults.getAddressSearchResults().stream().map(a -> new AddressSearchResultViewModel(a)).collect(Collectors.toList());
  }

}
