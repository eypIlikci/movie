package com.movie.dto;

import com.movie.entity.User;
import com.movie.entity.enums.Role;
import com.movie.validation.CreateUserMail;
import com.movie.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Email
    @CreateUserMail
    @NotEmpty
    private String email;

    @Password
    @Size(min = 6,message = "Şifre en az 6 karekter olmalı")
    private String password;

    @NotEmpty
    @Size(min = 5,message = "Ad-Soyad en az 5 karekter olmalı")
    private String name;

    public User getUser(){
        User user=new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setRole(Role.STANDARD);
        return user;
    }

}
