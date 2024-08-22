package com.example.elearningspringwithmongodb.features.category;

import com.example.elearningspringwithmongodb.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findCategoryById(String id);

    List<Category> findAllByIsDeletedIsTrue();

    Optional<Category> findCategoryByNameEqualsIgnoreCase(String name);

}
