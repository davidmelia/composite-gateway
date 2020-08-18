package com.example.demo.controller.customer.viewmodel;

import com.example.demo.model.customer.Customer;
import lombok.Value;

@Value
public class CustomerViewModel {

  private final String name;

  public CustomerViewModel(Customer customer) {
    this.name = customer.getName();
  }

  public CustomerViewModel() {
    this.name = null;
  }
}
