package com.movie.dto;

import com.movie.validation.ClassName;
import com.movie.validation.UniqueField;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CreateCategory {
    @Size(min = 3)
    @UniqueField(className = ClassName.CATEGORY)
    private String name;
}
