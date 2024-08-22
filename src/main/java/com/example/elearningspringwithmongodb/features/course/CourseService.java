package com.example.elearningspringwithmongodb.features.course;

import com.example.elearningspringwithmongodb.features.course.dto.*;

public interface CourseService {

    CourseCreateResponse createCourse(CourseCreateRequest request);

    SectionCreateResponse addCourseToSection(String courseId, SectionCreateRequest sectionCreateRequest);

    VideoCreateResponse addVideoToSection(String courseId, VideoCreateRequest videoCreateRequest);
}
