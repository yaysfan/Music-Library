package com.yayfan.music.domain.playlist;

import com.yayfan.music.domain.ResourceNotFoundException;

public class PlaylistNotFoundException extends ResourceNotFoundException {
    public PlaylistNotFoundException() {
        super("Playlist not found");
    }
}