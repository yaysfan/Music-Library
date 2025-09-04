package com.yayfan.music.persistence.song;

import com.yayfan.music.domain.song.Song;
import com.yayfan.music.persistence.AbstractEntity;
import com.yayfan.music.persistence.artist.ArtistEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
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


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;
}
