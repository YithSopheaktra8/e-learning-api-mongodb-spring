package com.example.elearningspringwithmongodb.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "sections")
@Getter
@Setter
@AllArgsConstructor
public class Section {

    private String title;

    private Integer orderNo;

    @DocumentReference
    private Course course;

    private List<Video> videos;

}
