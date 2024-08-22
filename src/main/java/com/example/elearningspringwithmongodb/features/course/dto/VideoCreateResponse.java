package com.example.elearningspringwithmongodb.features.course.dto;

public record VideoCreateResponse(
        Integer sectionOrderNo,
        Integer orderNo,
        String title,
        String fileName
) {
}
