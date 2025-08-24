package com.yayfan.music.domain.song;

import com.yayfan.music.api.song.NewSongRequestDto;
import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.file.FileAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SongService {
    private final SongStorage songStorage;
    private final FileAdapter fileAdapter;

    public void createSong(NewSongRequestDto dto, Artist artist) throws IOException {
        Song song = Song.builder()
                .name(dto.getName())
                .genre(dto.getGenre())
                .file(UUID.randomUUID() + ".mp3")
                .artist(artist)
                .build();
        songStorage.save(song);

        fileAdapter.save(song.getFile(), dto.getFile().getInputStream());
    }

    public Optional<Song> getSong(Integer id) {
        return songStorage.findById(id);
    }

    public InputStream loadSong(String fileName) {
        return fileAdapter.load(fileName);
    }

    public List<Song> searchSongs(String search) {
        return songStorage.search(search);
    }
}
