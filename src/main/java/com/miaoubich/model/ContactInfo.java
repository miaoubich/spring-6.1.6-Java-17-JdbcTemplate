package com.miaoubich.model;

public class ContactInfo {

	private Long id;
	private String email;
	private String phoneNumber;
	private Address address;
	
	public ContactInfo() {}
	public ContactInfo(String email, String phoneNumber, Address address) {
		super();
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public Long getId() {
		return id;
	}
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

	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "ContactInfo [email=" + email + ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}
	
}
