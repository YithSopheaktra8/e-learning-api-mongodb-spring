package com.example.elearningspringwithmongodb.features.category;


import com.example.elearningspringwithmongodb.features.category.dto.CategoryCreateRequest;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryResponse;
import com.example.elearningspringwithmongodb.features.category.dto.CategoryUpdateRequest;
import com.example.elearningspringwithmongodb.features.category.dto.PopularCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createCategory(@RequestBody @Valid CategoryCreateRequest request){
        categoryService.createCategory(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryById(@PathVariable String categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{categoryId}")
    public void updateCategoryById(@PathVariable String categoryId, @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest){
        categoryService.updateCategoryById(categoryId, categoryUpdateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategoryById(@PathVariable String categoryId){
        categoryService.deleteCategoryById(categoryId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{categoryId}/enable")
    public void enableCategoryById(@PathVariable String categoryId){
        categoryService.enableCategoryById(categoryId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{categoryId}/disable")
    public void disableCategoryById(@PathVariable String categoryId){
        categoryService.disableCategoryById(categoryId);
    }

    @GetMapping("/popular")
    public List<PopularCategoryResponse> getPopularCategory(){
        return categoryService.getPopularCategory();
    }

}
