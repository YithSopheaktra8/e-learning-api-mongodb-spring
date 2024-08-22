package com.example.elearningspringwithmongodb.features.category;

import com.example.elearningspringwithmongodb.domain.Category;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryCreateRequest;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryResponse;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryUpdateRequest;
import com.example.elearningspringwithmongodb.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void createCategory(CategoryCreateRequest request) {
        Category category = categoryMapper.fromCategoryCreateRequest(request);
        category.setIsDeleted(false);
        category.setIcon("https://localhost:8080/"+request.icon());
        categoryRepository.insert(category);
    }

    @Override
    public CategoryResponse getCategoryById(String categoryId) {

        Category category = categoryRepository.findCategoryById(categoryId)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found"
                ));

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void updateCategoryById(String id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findCategoryById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found"
                ));

        category.setName(request.name());

        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryMapper.toCategoryResponseList(categoryRepository.findAllByIsDeletedIsTrue());
    }

    @Override
    public void deleteCategoryById(String id) {
        Category category = categoryRepository.findCategoryById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found"
                ));

        categoryRepository.delete(category);
    }

    @Override
    public void enableCategoryById(String id) {
        Category category = categoryRepository.findCategoryById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found"
                ));

        category.setIsDeleted(true);

        categoryRepository.save(category);
    }

    @Override
    public void disableCategoryById(String id) {
        Category category = categoryRepository.findCategoryById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found"
                ));

        category.setIsDeleted(false);

        categoryRepository.save(category);
    }
}
