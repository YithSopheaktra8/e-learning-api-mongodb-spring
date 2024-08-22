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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CategoryRepository categoryRepository;
    private final SectionMapper sectionMapper;
    private final VideoMapper videoMapper;
    private final SectionRepository sectionRepository;
    private final VideoRepository videoRepository;


    @Override
    public CourseCreateResponse createCourse(CourseCreateRequest courseCreateRequest) {

        Course course = courseMapper.fromCourseCreateRequest(courseCreateRequest);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        course.setIsDraft(true);
        course.setIsPaid(false);
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

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        Section section = sectionMapper.fromSectionCreateRequest(sectionCreateRequest);

        if(sectionCreateRequest.videos() != null){
            Video video = videoMapper.fromVideoCreateRequest(sectionCreateRequest.videos().get(0));
            video = videoRepository.save(video);
            section.setVideos(List.of(video));
        }else{
            section.setVideos(new ArrayList<>());
        }

        section.setCourse(course);
        sectionRepository.save(section);

        return sectionMapper.fromSectionToSectionCreateResponse(section);
    }

    @Override
    public VideoCreateResponse addVideoToSection(String courseId, VideoCreateRequest videoCreateRequest) {
        return null;
    }

//    @Override
//    public VideoCreateResponse addVideoToSection(String courseId, VideoCreateRequest videoCreateRequest) {
//
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        String.format("Course with id %s not found", courseId)
//                ));
//
//
//        return videoMapper.fromVideoToVideoCreateResponse(video);
//    }
}
