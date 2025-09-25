package com.miaoubich.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.miaoubich.dto.ContactInfoRequest;
import com.miaoubich.dto.ContactInfoResponse;
import com.miaoubich.model.ContactInfo;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface ContactInfoMapper {
	@Mapping(source = "addressRequest", target = "address")
    ContactInfo toEntity(ContactInfoRequest request);
    ContactInfoResponse toResponse(ContactInfo contactInfo);
}

