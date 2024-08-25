package com.example.elearningspringwithmongodb.features.course;

import com.example.elearningspringwithmongodb.domain.Course;
import com.example.elearningspringwithmongodb.features.course.dto.*;
import org.springframework.data.domain.Page;

public interface CourseService {

    CourseCreateResponse createCourse(CourseCreateRequest request);

    SectionCreateResponse addCourseToSection(String courseId, SectionCreateRequest sectionCreateRequest);

    VideoCreateResponse addVideoToSection(String courseId, VideoCreateRequest videoCreateRequest);

    Page<?> getCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType);

    void updateCourse(String courseId, CourseUpdateRequest courseUpdateRequest);

    void deleteCourse(String courseId);

    void visibilityCourse(String courseId, Boolean visibility);

    void updateIsPaid(String courseId, Boolean isPaid);

    void updateThumbnail(String courseId, String thumbnail);

    void enableCourse(String courseId);

    void disableCourse(String courseId);

    Page<?> getFreeCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType);

    Page<?> getPrivateCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType);

    Page<?> getPublicCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType);

    Page<CourseSnippet> search(int page, int size, String filterAnd, String filterOr, String orders);

}
