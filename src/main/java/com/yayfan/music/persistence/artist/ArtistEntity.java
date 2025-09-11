package com.yayfan.music.persistence.artist;

import com.yayfan.music.persistence.AbstractEntity;
import com.yayfan.music.persistence.song.SongEntity;
import com.yayfan.music.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "artists")
public class ArtistEntity extends AbstractEntity {
    @Column(unique = true)
    private String artistName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ToString.Exclude
    @OneToMany(mappedBy = "artist", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    private List<SongEntity> songs = new ArrayList<>();
}
