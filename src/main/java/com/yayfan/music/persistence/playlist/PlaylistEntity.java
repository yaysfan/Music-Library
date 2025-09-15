package com.yayfan.music.persistence.playlist;

import com.yayfan.music.persistence.AbstractEntity;
import com.yayfan.music.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "playlists")
public class PlaylistEntity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(
            mappedBy = "playlist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PlaylistSongEntity> songs = new ArrayList<>();
}