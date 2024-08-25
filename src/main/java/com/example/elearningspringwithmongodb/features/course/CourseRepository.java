package com.example.elearningspringwithmongodb.features.course;

import com.example.elearningspringwithmongodb.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {


    Optional<Course> findCourseById(String id);

    List<Course> findAllByIsDraftIsFalseAndIsDeletedIsFalse();

    List<Course> findAllByIsDraftIsTrueAndIsDeletedIsFalse();

    List<Course> findAllByIsDraftIsFalseAndIsDeletedIsFalseAndPrice(Double price);

    List<Course> findAllByCategoryNameAndIsDeletedFalse(String categoryName);
}
