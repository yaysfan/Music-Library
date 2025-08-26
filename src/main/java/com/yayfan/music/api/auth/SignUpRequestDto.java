package com.yayfan.music.api.auth;

import lombok.*;

@Value
public class SignUpRequestDto {
    String username;
    String password;
    String artistName;
}
