package com.fastcampus.getinline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;

    public static LoginRequest of(String email, String password) {
        return new LoginRequest(email, password);
    }
}
