package com.yayfan.music.domain.song;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException() {
        super("Song not found");
    }
}
