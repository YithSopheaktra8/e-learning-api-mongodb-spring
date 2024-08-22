package com.example.elearningspringwithmongodb.features.course;

import com.example.elearningspringwithmongodb.features.course.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CourseCreateResponse createCourse(CourseCreateRequest request){
        return courseService.createCourse(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{courseId}/sections")
    public SectionCreateResponse addCourseToSection(@PathVariable String courseId,
                                                    @RequestBody SectionCreateRequest sectionCreateRequest){
        return courseService.addCourseToSection(courseId, sectionCreateRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{courseId}/videos")
    public VideoCreateResponse addVideoToSection(@PathVariable String courseId,
                                                 @RequestBody VideoCreateRequest videoCreateRequest){
        return courseService.addVideoToSection(courseId, videoCreateRequest);
    }

}
