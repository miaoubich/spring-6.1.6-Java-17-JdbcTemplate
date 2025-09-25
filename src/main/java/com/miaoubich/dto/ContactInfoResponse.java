package com.miaoubich.dto;

public record ContactInfoResponse(
		String email, 
		String phoneNumber, 
		AddressResponse addressResponse) {

}
