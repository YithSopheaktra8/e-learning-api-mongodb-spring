package com.example.elearningspringwithmongodb.features.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopularCategoryResponse {
    String name;
    String icon;
    Long totalCourse;
}
