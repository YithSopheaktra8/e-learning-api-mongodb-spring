package com.example.elearningspringwithmongodb.features.category.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryCreateRequest(
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Icon is required")
        String icon
) {
}
