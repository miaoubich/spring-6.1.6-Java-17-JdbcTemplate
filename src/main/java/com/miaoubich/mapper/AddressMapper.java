package com.miaoubich.mapper;

import org.mapstruct.Mapper;

import com.miaoubich.dto.AddressRequest;
import com.miaoubich.dto.AddressResponse;
import com.miaoubich.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	Address toEntity(AddressRequest request);
	AddressResponse toResponse(Address student);
}
