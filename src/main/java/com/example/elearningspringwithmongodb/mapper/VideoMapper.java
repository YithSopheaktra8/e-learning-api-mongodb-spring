package com.example.elearningspringwithmongodb.mapper;

import com.example.elearningspringwithmongodb.domain.Video;
import com.example.elearningspringwithmongodb.features.course.dto.VideoCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    VideoCreateResponse fromVideoToVideoCreateResponse(Video video);

}
