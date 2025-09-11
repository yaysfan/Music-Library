package com.yayfan.music.persistence.song;

import com.yayfan.music.domain.song.Song;
import com.yayfan.music.domain.song.SongStorage;
import com.yayfan.music.persistence.artist.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SongStore implements SongStorage {
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final SongEntityMapper songEntityMapper;

    @Override
    public Song save(Song song) {
        SongEntity songEntity = songEntityMapper.toEntity(song);

        artistRepository.findById(song.getArtist().getId())
                .ifPresent(songEntity::setArtist);

        return songEntityMapper.toDomain(songRepository.save(songEntity));
    }

    @Override
    public Optional<Song> findById(Integer id) {
        Optional<SongEntity> songEntity = songRepository.findById(id);
        return songEntity.map(songEntityMapper::toDomainWithArtist);
    }

    @Override
    public List<Song> search(String search) {
        List<SongEntity> songEntities = songRepository.search(search);
        return songEntities.stream()
                .map(songEntityMapper::toDomainWithArtist)
                .toList();
    }

    @Override
    public List<Song> findByArtistId(Integer artistId) {
        List<SongEntity> songEntities = songRepository.findByArtistId(artistId);
        return songEntities.stream()
                .map(songEntityMapper::toDomainWithArtist)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        songRepository.deleteById(id);
    }

}