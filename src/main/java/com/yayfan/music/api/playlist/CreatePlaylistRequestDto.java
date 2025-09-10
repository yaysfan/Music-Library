package com.yayfan.music.api.playlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePlaylistRequestDto(
        @NotBlank(message = "Playlist name is required")
        @Size(min = 1, max = 100, message = "Playlist name must be between 1 and 100 characters")
        String name
) {
}