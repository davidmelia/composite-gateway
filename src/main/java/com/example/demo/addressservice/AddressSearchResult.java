package com.example.demo.addressservice;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class AddressSearchResult {
  String id;
  boolean address;
  String text;
  String description;
}