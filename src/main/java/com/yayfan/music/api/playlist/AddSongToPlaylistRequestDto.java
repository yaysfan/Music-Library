package com.yayfan.music.api.playlist;

import jakarta.validation.constraints.NotNull;

public record AddSongToPlaylistRequestDto(
        @NotNull(message = "Song ID 를 입력해주세요")
        Integer songId
) {
}