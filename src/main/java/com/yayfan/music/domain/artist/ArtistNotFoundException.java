package com.yayfan.music.domain.artist;

import com.yayfan.music.domain.ResourceNotFoundException;

public class ArtistNotFoundException extends ResourceNotFoundException {
    public ArtistNotFoundException() {
        super("Artist not found");
    }
}
