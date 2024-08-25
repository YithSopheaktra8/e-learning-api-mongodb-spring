package com.example.elearningspringwithmongodb.features.category;

import com.example.elearningspringwithmongodb.features.category.dto.CategoryCreateRequest;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryResponse;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryUpdateRequest;
import com.example.elearningspringwithmongodb.features.category.dto.PopularCategoryResponse;

import java.util.List;

public interface CategoryService {

    void createCategory(CategoryCreateRequest request);

    CategoryResponse getCategoryById(String id);

    void updateCategoryById(String id, CategoryUpdateRequest request);

    List<CategoryResponse> getAllCategories();

    void deleteCategoryById(String id);

    void enableCategoryById(String id);

    void disableCategoryById(String id);

    List<PopularCategoryResponse> getPopularCategory();

}
