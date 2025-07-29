package com.example.parental_control_system.dto;

import com.example.parental_control_system.dto.ChildResponse;
import com.example.parental_control_system.dto.CreateChildRequest;
import com.example.parental_control_system.entity.Child;
import org.xmlunit.util.Mapper;

@Mapper(componentModel = "spring")
public interface ChildMapper {
    ChildMapper INSTANCE = Mappers.getMapper(ChildMapper.class);

    Child toEntity(CreateChildRequest dto);
    ChildResponse toDto(Child entity);
}