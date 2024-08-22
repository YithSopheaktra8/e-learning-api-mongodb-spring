package com.example.elearningspringwithmongodb.features.course;

import com.example.elearningspringwithmongodb.domain.Course;
import com.example.elearningspringwithmongodb.domain.Section;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends MongoRepository<Section, String> {

}
