package com.example.elearningspringwithmongodb.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Section {

    private String title;
    private Integer orderNo;
    private List<Video> videos;
}
