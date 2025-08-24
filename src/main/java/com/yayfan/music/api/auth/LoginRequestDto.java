package com.yayfan.music.api.auth;

import lombok.*;

@Value
public class LoginRequestDto {
    String username;
    String password;
}
