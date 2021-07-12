package com.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    @Email(message = "Email geçerli tipde değil!(örnek@örnek.com)")
    private String email;
}
