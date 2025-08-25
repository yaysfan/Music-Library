package com.yayfan.music.domain.artist;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException() {
        super("Artist not found");
    }
}
