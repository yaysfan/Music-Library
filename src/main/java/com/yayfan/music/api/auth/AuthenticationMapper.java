package com.yayfan.music.api.auth;

import com.yayfan.music.domain.auth.AuthRequest;
import com.yayfan.music.domain.auth.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {
    AuthRequest toAuthRequest(LoginRequestDto request);

    SignUpRequest toSignUpRequest(SignUpRequestDto request);
}