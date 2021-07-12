package com.movie.dto;

import com.movie.validation.ClassName;
import com.movie.validation.StaticValidator;
import com.movie.validation.UniqueField;
import com.movie.validation.UniqueUpdateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {
    @NotNull
    private Long id;
    @Size(min = 3)
    private String name;

}
