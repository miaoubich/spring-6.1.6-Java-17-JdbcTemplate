package com.miaoubich.mapper;

import org.mapstruct.Mapper;

import com.miaoubich.dto.AcademicInfoRequest;
import com.miaoubich.dto.AcademicInfoResponse;
import com.miaoubich.model.AcademicInfo;

@Mapper(componentModel = "spring")
public interface AcademicInfoMapper {
    AcademicInfo toEntity(AcademicInfoRequest request);
    AcademicInfoResponse toResponse(AcademicInfo academicInfo);
}

