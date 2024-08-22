package com.example.elearningspringwithmongodb.features.course.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CourseCreateRequest(
        @NotEmpty(message = "Title is required")
        String title,
        @NotEmpty(message = "Slug is required")
        String slug,
        @NotEmpty(message = "Category name is required")
        String categoryName,
        @NotEmpty(message = "Instructor name is required")
        String description,
        @NotEmpty(message = "Description is required")
        String thumbnail,
        @NotNull(message = "Price is required")
        Double price
) {
}
