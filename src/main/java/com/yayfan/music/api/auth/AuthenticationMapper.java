package com.yayfan.music.api.auth;

import com.yayfan.music.domain.auth.AuthRequest;
import com.yayfan.music.domain.auth.SignUpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {
    public AuthRequest toAuthRequest(LoginRequestDto request) {
        return new AuthRequest(
                request.getUsername(),
                request.getPassword()
        );
    }

    public SignUpRequest toSignUpRequest(SignUpRequestDto request) {
        return new SignUpRequest(
                request.getUsername(),
                request.getPassword(),
                request.getArtistName()
        );
    }
}
