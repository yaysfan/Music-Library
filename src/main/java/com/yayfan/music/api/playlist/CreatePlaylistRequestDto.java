package com.yayfan.music.api.playlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePlaylistRequestDto(
        @NotBlank(message = "Playlist 이름 을 입력해주세요")
        @Size(min = 1, max = 100, message = "Playlist 이름은 1글자에서 100글자 사이여야 합니다")
        String name
) {
}