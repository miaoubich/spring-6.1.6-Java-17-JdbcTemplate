package com.miaoubich.dto;

import com.miaoubich.model.Address;

public record ContactInfoRequest(String email, String phoneNumber, Address address) {

}
