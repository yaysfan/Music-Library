package com.yayfan.music.domain.song;

import com.yayfan.music.domain.ResourceNotFoundException;

public class SongNotFoundException extends ResourceNotFoundException {
    public SongNotFoundException() {
        super("Song not found");
    }
}
