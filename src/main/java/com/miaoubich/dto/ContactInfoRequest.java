package com.miaoubich.dto;

public record ContactInfoRequest(
		String email, 
		String phoneNumber, 
		AddressRequest addressRequest) {

}
