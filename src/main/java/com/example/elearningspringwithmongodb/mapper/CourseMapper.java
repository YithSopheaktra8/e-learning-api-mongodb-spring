package com.example.elearningspringwithmongodb.mapper;

import com.example.elearningspringwithmongodb.domain.Course;
import com.example.elearningspringwithmongodb.features.course.dto.CourseCreateRequest;
import com.example.elearningspringwithmongodb.features.course.dto.CourseCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course fromCourseCreateRequest(CourseCreateRequest request);

    CourseCreateResponse toCourseCreateResponse(Course course);

}
