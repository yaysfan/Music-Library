package com.yayfan.music.api.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
public class LoginRequestDto {
    @NotBlank(message = "Username is required")
    String username;

    @NotBlank(message = "Password is required")
    String password;
}
