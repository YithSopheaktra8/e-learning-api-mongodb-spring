package com.example.elearningspringwithmongodb.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Video {
    private Integer orderNo;
    private String title;
    private String fileName;
    private Integer orderSectionNo;
}
