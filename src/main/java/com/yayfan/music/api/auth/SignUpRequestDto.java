package com.yayfan.music.api.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Value
public class SignUpRequestDto {
    @NotNull(message = "Username 을 입력해주세요")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    String username;

    @NotNull(message = "Password 을 입력해주세요")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,}$",
            message = "Password 는 최소 8글자, 대문자 1개 , 소문자1개 , 숫자 하나와 특수문자 하나를 포함해야 합니다.")
    String password;

    @NotNull(message = "Artist 을 입력해주세요")
    @Size(min = 2, max = 30, message = "Artist name 은 최소 2글자에서 30글자 입니다.")
    String artistName;
}
