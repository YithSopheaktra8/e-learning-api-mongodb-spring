package com.example.elearningspringwithmongodb.features.course.dto;

import com.example.elearningspringwithmongodb.domain.Section;

import java.util.List;

public record CourseContentDetailResponse(
        String id,
        String title,
        String slug,
        String description,
        Double price,
        Double discount,
        Boolean isPaid,
        Boolean isDraft,
        String thumbnail,
        String categoryName,
        String instructorName,
        List<Section> sections,
        String createdAt,
        String updatedAt
) {

}
