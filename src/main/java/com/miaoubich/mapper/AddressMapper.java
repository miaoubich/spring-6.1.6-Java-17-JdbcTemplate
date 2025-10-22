package com.miaoubich.mapper;

import com.miaoubich.dto.AddressRequest;
import com.miaoubich.dto.AddressResponse;
import com.miaoubich.model.Address;

public class AddressMapper {

	public static Address toEntity(AddressRequest request) {
		return StudentMapper.mapper.map(request, Address.class);
	}

	public static AddressResponse toResponse(Address address) {
		return StudentMapper.mapper.map(address, AddressResponse.class);
	}
}
