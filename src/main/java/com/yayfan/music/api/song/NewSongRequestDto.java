package com.yayfan.music.api.song;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class NewSongRequestDto {
    @NotNull(message = "Song 이름 을 입력해주세요")
    @Size(min = 1, max = 50, message = "Song 이름은 최소 1글자에서 50글자여야 합니다")
    String name;

    @NotNull(message = "Genre 을 입력해주세요")
    @Size(min = 1, max = 50, message = "Artist 이름은 최소 1글자에서 50글자여야 합니다")
    String genre;

    @NotNull(message = "Song file 을 입력해주세요")
    MultipartFile file;
}
