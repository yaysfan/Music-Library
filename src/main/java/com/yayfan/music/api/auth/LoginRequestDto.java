package com.yayfan.music.api.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
public class LoginRequestDto {
    @NotBlank(message = "Username 을 입력해주세요")
    String username;

    @NotBlank(message = "Password 을 입력해주세요")
    String password;
}
