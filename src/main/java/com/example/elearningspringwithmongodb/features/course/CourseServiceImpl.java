package com.example.elearningspringwithmongodb.features.course;


import com.example.elearningspringwithmongodb.domain.Category;
import com.example.elearningspringwithmongodb.domain.Course;
import com.example.elearningspringwithmongodb.domain.Section;
import com.example.elearningspringwithmongodb.domain.Video;
import com.example.elearningspringwithmongodb.features.category.CategoryRepository;
import com.example.elearningspringwithmongodb.features.course.dto.*;
import com.example.elearningspringwithmongodb.mapper.CourseMapper;
import com.example.elearningspringwithmongodb.mapper.SectionMapper;
import com.example.elearningspringwithmongodb.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CategoryRepository categoryRepository;
    private final SectionMapper sectionMapper;
    private final VideoMapper videoMapper;


    @Override
    public CourseCreateResponse createCourse(CourseCreateRequest courseCreateRequest) {

        Course course = courseMapper.fromCourseCreateRequest(courseCreateRequest);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        course.setIsDraft(true);
        course.setIsDraft(false);
        course.setDiscount(0.0);
        course.setThumbnail("https://localhost:8080/"+courseCreateRequest.thumbnail());
        course.setInstructorName("YITH SOPHEAKTRA");
        course.setSections(new ArrayList<>());

        Category category = categoryRepository.findCategoryByNameEqualsIgnoreCase(courseCreateRequest.categoryName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Category with name %s not found", courseCreateRequest.categoryName())
                ));

        course.setCategoryName(category.getName());

        course = courseRepository.insert(course);

        return courseMapper.toCourseCreateResponse(course);
    }

    @Override
    public SectionCreateResponse addCourseToSection(String courseId, SectionCreateRequest sectionCreateRequest) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        Section section = sectionMapper.fromSectionCreateRequest(sectionCreateRequest);

        if(course.getSections() == null){
            course.setSections(new ArrayList<>());
        }

        course.getSections().add(section);

        courseRepository.save(course);

        return sectionMapper.toSectionCreateResponse(section);
    }

    @Override
    public VideoCreateResponse addVideoToSection(String courseId, VideoCreateRequest videoCreateRequest) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        Section section = course.getSections().stream()
                .filter(s -> s.getOrderNo().equals(videoCreateRequest.sectionOrderNo()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Section with id %s not found", videoCreateRequest.sectionOrderNo())
                ));

        Video video = Video.builder()
                .orderSectionNo(videoCreateRequest.sectionOrderNo())
                .fileName(videoCreateRequest.fileName())
                .title(videoCreateRequest.title())
                .orderNo(videoCreateRequest.orderNo())
                .build();

        if(section.getVideos() == null){
            section.setVideos(new ArrayList<>());
        }

        section.getVideos().add(video);

        courseRepository.save(course);

        return videoMapper.fromVideoToVideoCreateResponse(video);
    }

    @Override
    public Page<?> getCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType) {

        List<Course> courses = courseRepository.findAll();

        List<?> processedCourses = switch (responseType) {
            case SNIPPET -> courses.stream()
                    .map(courseMapper::toSnippetResponse)
                    .collect(Collectors.toList());
            case CONTENT_DETAIL -> courses.stream()
                    .map(courseMapper::toDetailResponse)
                    .collect(Collectors.toList());
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Response type %s not supported", responseType)
            );
        };

        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + size), processedCourses.size());

        return new PageImpl<>(processedCourses.subList(start, end), PageRequest.of(page, size), processedCourses.size());
    }

    @Override
    public void updateCourse(String courseId, CourseUpdateRequest courseUpdateRequest) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        courseMapper.updateCourseFromCourseUpdateRequest(courseUpdateRequest, course);

        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String courseId) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        courseRepository.delete(course);


    }

    @Override
    public void visibilityCourse(String courseId, Boolean visibility) {

            Course course = courseRepository.findCourseById(courseId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            String.format("Course with id %s not found", courseId)
                    ));

            course.setIsDraft(visibility);

            courseRepository.save(course);
    }

    @Override
    public void updateIsPaid(String courseId, Boolean isPaid) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        course.setIsPaid(isPaid);

    }

    @Override
    public void updateThumbnail(String courseId, String thumbnail) {

            Course course = courseRepository.findCourseById(courseId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            String.format("Course with id %s not found", courseId)
                    ));

            course.setThumbnail(thumbnail);
    }

    @Override
    public void enableCourse(String courseId) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        course.setIsDeleted(false);
    }

    @Override
    public void disableCourse(String courseId) {

            Course course = courseRepository.findCourseById(courseId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            String.format("Course with id %s not found", courseId)
                    ));

            course.setIsDeleted(true);
    }

    @Override
    public Page<?> getPrivateCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType) {

        List<Course> courses = courseRepository.findAllByIsDraftIsTrueAndIsDeletedIsFalse();

        List<?> processedCourses = switch (responseType) {
            case SNIPPET -> courses.stream()
                    .map(courseMapper::toSnippetResponse)
                    .collect(Collectors.toList());
            case CONTENT_DETAIL -> courses.stream()
                    .map(courseMapper::toDetailResponse)
                    .collect(Collectors.toList());
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Response type %s not supported", responseType)
            );
        };

        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + size), processedCourses.size());

        return new PageImpl<>(processedCourses.subList(start, end), PageRequest.of(page, size), processedCourses.size());

    }

    @Override
    public Page<?> getPublicCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType) {


        List<Course> courses = courseRepository.findAllByIsDraftIsFalseAndIsDeletedIsFalse();

        List<?> processedCourses = switch (responseType) {
            case SNIPPET -> courses.stream()
                    .map(courseMapper::toSnippetResponse)
                    .collect(Collectors.toList());
            case CONTENT_DETAIL -> courses.stream()
                    .map(courseMapper::toDetailResponse)
                    .collect(Collectors.toList());
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Response type %s not supported", responseType)
            );
        };

        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + size), processedCourses.size());

        return new PageImpl<>(processedCourses.subList(start, end), PageRequest.of(page, size), processedCourses.size());
    }


}
