package com.yayfan.music.persistence.artist;

import com.yayfan.music.persistence.AbstractEntity;
import com.yayfan.music.persistence.song.SongEntity;
import com.yayfan.music.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "artists")
public class ArtistEntity extends AbstractEntity {
    @Column(nullable = false)
    private String artistName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "artist", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    private List<SongEntity> songs;
}
