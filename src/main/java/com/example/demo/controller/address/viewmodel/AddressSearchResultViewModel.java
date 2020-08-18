package com.example.demo.controller.address.viewmodel;

import com.example.demo.model.addresss.AddressSearchResult;
import lombok.Value;

@Value
public class AddressSearchResultViewModel {
  private final String id;
  private final String text;
  private final String description;

  public AddressSearchResultViewModel(AddressSearchResult addressSearchResult) {
    this.id = addressSearchResult.getId();
    this.text = addressSearchResult.getText();
    this.description = addressSearchResult.getDescription();
  }

  public AddressSearchResultViewModel() {
    this.id = null;
    this.text = null;
    this.description = null;
  }
}
