package com.example.elearningspringwithmongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "videos")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    private String id;
    private Integer orderNo;
    private String title;
    private String fileName;
    private Integer orderSectionNo;

}
