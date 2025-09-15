package com.yayfan.music.persistence.playlist;

import com.yayfan.music.persistence.AbstractEntity;
import com.yayfan.music.persistence.song.SongEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "playlist_songs")
@NoArgsConstructor
public class PlaylistSongEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private SongEntity song;

}