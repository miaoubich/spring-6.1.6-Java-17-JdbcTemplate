package com.miaoubich.mapper;

import com.miaoubich.dto.AcademicInfoRequest;
import com.miaoubich.dto.AcademicInfoResponse;
import com.miaoubich.model.AcademicInfo;

public class AcademicInfoMapper {

	public static AcademicInfo toEntity(AcademicInfoRequest request) {
		return StudentMapper.mapper.map(request, AcademicInfo.class);
	}

	public static AcademicInfoResponse toResponse(AcademicInfo academicInfo) {
		return StudentMapper.mapper.map(academicInfo, AcademicInfoResponse.class);
	}
}
