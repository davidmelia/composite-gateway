package com.example.demo.model.addresss;

import java.util.List;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class AddressSearchResults {
  private final List<AddressSearchResult> addressSearchResults;

}