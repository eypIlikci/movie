package com.movie.dto;

import com.movie.validation.ClassName;
import com.movie.validation.UniqueField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateActorRequest {
    @Size(min = 3)
    @UniqueField(className = ClassName.ACTOR,
            message = "Bu aktor kayıtlı!")
    private String name;
}
