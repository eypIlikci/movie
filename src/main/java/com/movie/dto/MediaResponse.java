package com.movie.dto;

import com.movie.entity.Media;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaResponse {
    private Long id;
    private String name;
    public static MediaResponse getInstance(Media media){
        return new MediaResponse(media.getId(),media.getName());
    }
}
