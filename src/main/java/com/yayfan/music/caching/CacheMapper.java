package com.yayfan.music.caching;

import com.yayfan.music.caching.artist.ArtistCacheDto;
import com.yayfan.music.caching.song.SongCacheDto;
import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.song.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CacheMapper {

    CacheMapper INSTANCE = Mappers.getMapper(CacheMapper.class);

    ArtistCacheDto artistToArtistCacheDto(Artist artist);

    SongCacheDto songToSongCacheDto(Song song);

    @Mapping(target = "songs", ignore = true)
    @Mapping(target = "user", ignore = true)
    Artist artistCacheDtoToArtist(ArtistCacheDto artistCacheDto);

    Song songCacheDtoToSong(SongCacheDto songCacheDto);

    List<SongCacheDto> songsToSongCacheDtos(List<Song> songs);

    List<Song> songCacheDtosToSongs(List<SongCacheDto> songCacheDtos);
}

