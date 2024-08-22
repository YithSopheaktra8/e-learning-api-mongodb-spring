package com.example.elearningspringwithmongodb.features.course.dto;

import com.example.elearningspringwithmongodb.domain.Video;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SectionCreateRequest(
        @NotEmpty(message = "Title is required")
        String title,
        @NotNull(message = "Order number is required")
        Integer orderNo,

        List<VideoCreateRequest> videos
) {
}
