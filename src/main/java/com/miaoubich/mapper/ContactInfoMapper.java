package com.miaoubich.mapper;

import org.mapstruct.Mapper;

import com.miaoubich.dto.ContactInfoRequest;
import com.miaoubich.dto.ContactInfoResponse;
import com.miaoubich.model.ContactInfo;

@Mapper(componentModel = "spring")
public interface ContactInfoMapper {
	ContactInfo toEntity(ContactInfoRequest request);
	ContactInfoResponse toRequest(ContactInfo contactInfo);
}
