package com.yayfan.music.persistence.user;

import com.yayfan.music.domain.user.Role;
import com.yayfan.music.domain.user.User;
import com.yayfan.music.persistence.AbstractEntity;
import com.yayfan.music.persistence.artist.ArtistEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class UserEntity extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ArtistEntity artist;

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public User fromThis() {
        return User.builder()
                .id(getId())
                .username(getUsername())
                .password(getPassword())
                .role(getRole())
                .build();
    }
}
