package com.example.elearningspringwithmongodb.features.course;

import com.example.elearningspringwithmongodb.domain.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
