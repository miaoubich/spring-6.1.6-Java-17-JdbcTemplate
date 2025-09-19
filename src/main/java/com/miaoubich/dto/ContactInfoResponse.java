package com.miaoubich.dto;

import com.miaoubich.model.Address;

public record ContactInfoResponse(String email, String phoneNumber, Address address) {

}
