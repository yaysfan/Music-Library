package com.yayfan.music.api.artist;

import com.yayfan.music.domain.artist.Artist;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    SearchedArtistDto toSearchedArtistDto(Artist artist);
    List<SearchedArtistDto> toSearchedArtistDto(List<Artist> artists);
    ArtistDto toArtistDto(Artist artist);
}