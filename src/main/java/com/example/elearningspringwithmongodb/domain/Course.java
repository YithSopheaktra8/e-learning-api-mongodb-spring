package com.example.elearningspringwithmongodb.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "courses")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Course {

    @Id
    private String id;
    private String title;
    private String slug;
    private String description;
    private Double price;
    private Double discount;
    private Boolean isPaid;
    private Boolean isDraft;
    private String thumbnail;
    private String categoryName;
    private String instructorName;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Section> sections;

}
