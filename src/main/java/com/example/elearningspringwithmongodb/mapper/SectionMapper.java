package com.example.elearningspringwithmongodb.mapper;

import com.example.elearningspringwithmongodb.domain.Section;
import com.example.elearningspringwithmongodb.features.course.dto.SectionCreateRequest;
import com.example.elearningspringwithmongodb.features.course.dto.SectionCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    Section fromSectionCreateRequest(SectionCreateRequest sectionCreateRequest);

    SectionCreateResponse toSectionCreateResponse(Section section);

}
