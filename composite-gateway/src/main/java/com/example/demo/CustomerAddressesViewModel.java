package com.example.demo;

import java.util.List;

import com.example.demo.addressservice.AddressSearchResult;
import com.example.demo.addressservice.AddressSearchResults;
import com.example.demo.customerservice.Customer;

import lombok.Value;

@Value
public class CustomerAddressesViewModel {

	private final String name;
	private final List<AddressSearchResult> addresses;
	
	public CustomerAddressesViewModel(Customer customer, AddressSearchResults addresses) {
		this.name = customer.getName();
		this.addresses = addresses.getAddressSearchResults();
	}
	
}
