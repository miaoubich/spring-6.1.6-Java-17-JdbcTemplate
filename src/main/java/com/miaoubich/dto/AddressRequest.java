package com.miaoubich.dto;

public record AddressRequest(
		String street, 
		String city, 
		String zipCode, 
		String country) {

}
