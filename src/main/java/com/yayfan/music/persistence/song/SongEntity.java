package com.yayfan.music.persistence.song;

import com.yayfan.music.persistence.AbstractEntity;
import com.yayfan.music.persistence.artist.ArtistEntity;
import com.yayfan.music.persistence.playlist.PlaylistSongEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "songs")
public class SongEntity extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String file;

    @Column(nullable = false)
    private String genre;


    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    @ToString.Exclude
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistSongEntity> playlistSongs = new ArrayList<>();
}
