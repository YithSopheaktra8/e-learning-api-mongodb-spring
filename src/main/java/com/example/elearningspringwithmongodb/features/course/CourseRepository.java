package com.example.elearningspringwithmongodb.features.course;

import com.example.elearningspringwithmongodb.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
}
