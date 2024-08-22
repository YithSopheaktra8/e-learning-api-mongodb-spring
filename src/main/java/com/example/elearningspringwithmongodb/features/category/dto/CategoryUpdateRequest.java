package com.example.elearningspringwithmongodb.features.category.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryUpdateRequest(
        @NotEmpty(message = "Name is required")
        String name
) {
}
