package com.yayfan.music.domain.auth;

import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.artist.ArtistStorage;
import com.yayfan.music.domain.user.Role;
import com.yayfan.music.domain.user.User;
import com.yayfan.music.domain.user.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ArtistStorage artistStorage;
    private final UserStorage userStorage;
    private final PasswordEncoder passwordEncoder;
    private final JwtAdapter jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ARTIST)
                .build();

        Artist artist = Artist.builder()
                .artistName(request.getArtistName())
                .user(user)
                .build();

        artistStorage.save(artist);
        return jwtService.generateToken(artist.getUser());
    }

    public String authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userStorage.findByUsername(request.getUsername()).orElseThrow();
        return jwtService.generateToken(user);
    }
}
