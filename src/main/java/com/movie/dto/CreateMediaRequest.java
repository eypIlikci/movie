package com.movie.dto;

import com.movie.validation.ClassName;
import com.movie.validation.UniqueField;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CreateMediaRequest {
    @Size(min = 3)
    @UniqueField(className = ClassName.MEDIA,
            message = "Bu medya kayıt edilmiş!")
    private String name;
}
