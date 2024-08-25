package com.example.elearningspringwithmongodb.features.course;

import com.example.elearningspringwithmongodb.features.course.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public Page<?> getCourseList(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(defaultValue = "SNIPPET") CourseBaseResponse.ResponseType responseType){
        return courseService.getCourseList(page, size, responseType);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{courseId}")
    public void updateCourse(@PathVariable String courseId,
                             @RequestBody CourseUpdateRequest courseUpdateRequest){
        courseService.updateCourse(courseId, courseUpdateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable String courseId){
        courseService.deleteCourse(courseId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{courseId}/visibility")
    public void visibilityCourse(@PathVariable String courseId,
                                 @RequestParam Boolean visibility){
        courseService.visibilityCourse(courseId, visibility);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{courseId}/is-paid")
    public void updateIsPaid(@PathVariable String courseId,
                             @RequestParam Boolean isPaid){
        courseService.updateIsPaid(courseId, isPaid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{courseId}/thumbnail")
    public void updateThumbnail(@PathVariable String courseId,
                                @RequestBody String thumbnail){
        courseService.updateThumbnail(courseId, thumbnail);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{courseId}/enable")
    public void enableCourse(@PathVariable String courseId){
        courseService.enableCourse(courseId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{courseId}/disable")
    public void disableCourse(@PathVariable String courseId){
        courseService.disableCourse(courseId);
    }


    @GetMapping("/private")
    public Page<?> getPrivateCourseList(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(defaultValue = "SNIPPET") CourseBaseResponse.ResponseType responseType){
        return courseService.getPrivateCourseList(page, size, responseType);
    }

    @GetMapping("/public")
    public Page<?> getPublicCourseList(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam(defaultValue = "SNIPPET") CourseBaseResponse.ResponseType responseType){
        return courseService.getPublicCourseList(page, size, responseType);
    }

    @GetMapping("/free")
    public Page<?> getFreeCourseList(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(defaultValue = "SNIPPET") CourseBaseResponse.ResponseType responseType){
        return courseService.getFreeCourseList(page, size, responseType);
    }

    @GetMapping("/filter")
    public Page<CourseSnippet> search(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(required = false) String filterAnd,
                                      @RequestParam(required = false) String filterOr,
                                      @RequestParam(required = false) String orders){
        return courseService.search(page, size, filterAnd, filterOr, orders);
    }

}
