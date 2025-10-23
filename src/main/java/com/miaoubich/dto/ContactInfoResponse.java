package com.miaoubich.dto;

public class ContactInfoResponse {

	private String email;
	private String phoneNumber;
	private AddressResponse addressResponse;

	// No-argument constructor (required by ModelMapper)
	public ContactInfoResponse() {}

	// All-arguments constructor (optional for convenience)
	public ContactInfoResponse(String email, String phoneNumber, AddressResponse addressResponse) {
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.addressResponse = addressResponse;
	}

	// Getters and Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public AddressResponse getAddressResponse() {
		return addressResponse;
	}

	public void setAddressResponse(AddressResponse addressResponse) {
		this.addressResponse = addressResponse;
	}

	// toString for debugging/logging
	@Override
	public String toString() {
		return "ContactInfoResponse{" +
				"email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", addressResponse=" + addressResponse +
				'}';
	}
}
