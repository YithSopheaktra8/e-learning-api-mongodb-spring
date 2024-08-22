package com.example.elearningspringwithmongodb.features.course.dto;

public record VideoCreateRequest(
        Integer sectionOrderNo,
        Integer orderNo,
        String title,
        String fileName
) {
}
