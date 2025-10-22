package com.miaoubich.mapper;

import com.miaoubich.dto.ContactInfoRequest;
import com.miaoubich.dto.ContactInfoResponse;
import com.miaoubich.model.ContactInfo;

public class ContactInfoMapper {

	public static ContactInfo toEntity(ContactInfoRequest request) {
		return StudentMapper.mapper.map(request, ContactInfo.class);
	}

	public static ContactInfoResponse toResponse(ContactInfo contactInfo) {
		return StudentMapper.mapper.map(contactInfo, ContactInfoResponse.class);
	}
}
