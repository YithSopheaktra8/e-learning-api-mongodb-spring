package com.example.elearningspringwithmongodb.mapper;

import com.example.elearningspringwithmongodb.domain.Category;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryCreateRequest;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category fromCategoryCreateRequest(CategoryCreateRequest request);

    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}
