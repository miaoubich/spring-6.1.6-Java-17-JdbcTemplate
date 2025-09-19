package com.miaoubich.mapper;

import org.mapstruct.Mapper;

import com.miaoubich.dto.ContactInfoRequest;
import com.miaoubich.dto.ContactInfoResponse;
import com.miaoubich.model.ContactInfo;

@Mapper(componentModel = "spring")
public interface AcademicInfoMapper {
	ContactInfo toEntity(ContactInfoRequest request);
	ContactInfoResponse toResponse(ContactInfo contactInfo);
}
