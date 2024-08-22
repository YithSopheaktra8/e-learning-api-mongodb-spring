package com.example.elearningspringwithmongodb.mapper;

import com.example.elearningspringwithmongodb.domain.Course;
import com.example.elearningspringwithmongodb.domain.Section;
import com.example.elearningspringwithmongodb.features.course.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course fromCourseCreateRequest(CourseCreateRequest request);

    CourseCreateResponse toCourseCreateResponse(Course course);

    CourseSnippet toSnippetResponse(Course course);

    CourseContentDetailResponse toDetailResponse(Course course);

    void updateCourseFromCourseUpdateRequest(CourseUpdateRequest courseUpdateRequest, @MappingTarget Course course);
}
