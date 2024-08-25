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
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private final MongoTemplate mongoTemplate;


    @Override
    public CourseCreateResponse createCourse(CourseCreateRequest courseCreateRequest) {

        Course course = courseMapper.fromCourseCreateRequest(courseCreateRequest);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        course.setIsPaid(false);
        course.setIsDraft(true);
        course.setIsDeleted(false);
        course.setDiscount(0.0);
        course.setThumbnail("https://localhost:8080/" + courseCreateRequest.thumbnail());
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

        if (course.getSections() == null) {
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

        if (section.getVideos() == null) {
            section.setVideos(new ArrayList<>());
        }

        section.getVideos().add(video);

        courseRepository.save(course);

        return videoMapper.fromVideoToVideoCreateResponse(video);
    }

    @Override
    public Page<?> getCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType) {

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
        courseRepository.save(course);
    }

    @Override
    public void updateThumbnail(String courseId, String thumbnail) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        course.setThumbnail(thumbnail);
        courseRepository.save(course);
    }

    @Override
    public void enableCourse(String courseId) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        course.setIsDraft(false);
        courseRepository.save(course);
    }

    @Override
    public void disableCourse(String courseId) {

        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Course with id %s not found", courseId)
                ));

        course.setIsDeleted(true);
        courseRepository.save(course);
    }

    @Override
    public Page<?> getFreeCourseList(Integer page, Integer size, CourseBaseResponse.ResponseType responseType) {


        List<Course> courses = courseRepository.findAllByIsDraftIsFalseAndIsDeletedIsFalseAndPrice(0.0);

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


        return new PageImpl<>(processedCourses, PageRequest.of(page, size), processedCourses.size());
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

    @Override
    public Page<CourseSnippet> search(int page, int size, String filterAnd, String filterOr, String orders) {

        Query query = new Query();

        if(filterAnd != null && !filterAnd.isEmpty()){
            List<Criteria> andCriteria = parseFilterCriteria(filterAnd);
            query.addCriteria(new Criteria().andOperator(andCriteria.toArray(new Criteria[0])));
        }

        if(filterOr != null && !filterOr.isEmpty()){
            List<Criteria> orCriteria = parseFilterCriteria(filterOr);
            query.addCriteria(new Criteria().orOperator(orCriteria.toArray(new Criteria[0])));
        }

        if(orders != null && !orders.isEmpty()){
            Sort sort = parseSortOrders(orders);
            query.with(sort);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        query.with(pageRequest);

        List<Course> courses = mongoTemplate.find(query, Course.class);

        Query countQuery = Query.of(query).limit(-1).skip(-1);
        long total = mongoTemplate.count(countQuery, Course.class);

        return new PageImpl<>(courses.stream().map(courseMapper::toSnippetResponse).collect(Collectors.toList()), pageRequest, total);
    }

    private List<Criteria> parseFilterCriteria(String filter) {
        List<Criteria> criteriaList = new ArrayList<>();
        String[] conditions = filter.split(",");

        for (String condition : conditions) {
            String[] parts = condition.split("\\|");
            if (parts.length == 3) {
                String field = parts[0];       // Field name, e.g., "name", "address", etc.
                String operator = parts[1];    // Operator, e.g., "eq", "gt", "regex", etc.
                String value = parts[2];       // Value to compare against, e.g., "mengseu", "pp", etc.

                switch (operator.toLowerCase()) {
                    case "eq":  // Equals
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case "ne":  // Not Equals
                        criteriaList.add(Criteria.where(field).ne(value));
                        break;
                    case "gt":  // Greater Than
                        criteriaList.add(Criteria.where(field).gt(value));
                        break;
                    case "lt":  // Less Than
                        criteriaList.add(Criteria.where(field).lt(value));
                        break;
                    case "gte": // Greater Than or Equal To
                        criteriaList.add(Criteria.where(field).gte(value));
                        break;
                    case "lte": // Less Than or Equal To
                        criteriaList.add(Criteria.where(field).lte(value));
                        break;
                    case "in":  // In List (multiple values separated by ";")
                        criteriaList.add(Criteria.where(field).in(value.split(";")));
                        break;
                    case "nin": // Not In List (multiple values separated by ";")
                        criteriaList.add(Criteria.where(field).nin(value.split(";")));
                        break;
                    case "regex": // Regular Expression (case-insensitive)
                        criteriaList.add(Criteria.where(field).regex(value, "i"));
                        break;
                    case "exists": // Field Exists (true/false)
                        criteriaList.add(Criteria.where(field).exists(Boolean.parseBoolean(value)));
                        break;
                    default:
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid operator: " + operator);
                        // Add more operators as needed
                }
            }
        }
        return criteriaList;
    }

    private Sort parseSortOrders(String orders) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        String[] orderConditions = orders.split(",");
        for (String orderCondition : orderConditions) {
            String[] parts = orderCondition.split("\\|");
            if (parts.length == 2) {
                String field = parts[0];
                Sort.Direction direction = "desc".equalsIgnoreCase(parts[1]) ? Sort.Direction.DESC : Sort.Direction.ASC;
                sortOrders.add(new Sort.Order(direction, field));
            }
        }
        return Sort.by(sortOrders);
    }

}
