package com.example.elearningspringwithmongodb.features.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseUpdateRequest(

        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Slug is required")
        String slug,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Price is required")
        Double price,
        @NotNull(message = "Discount is required")
        Double discount,
        @NotBlank(message = "Thumbnail is required")
        String categoryName,

        Boolean isPaid,
        Boolean isDraft
) {
}
