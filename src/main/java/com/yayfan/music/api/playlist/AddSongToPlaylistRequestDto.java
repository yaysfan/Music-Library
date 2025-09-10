package com.yayfan.music.api.playlist;

import jakarta.validation.constraints.NotNull;

public record AddSongToPlaylistRequestDto(
        @NotNull(message = "Song ID is required")
        Integer songId
) {
}