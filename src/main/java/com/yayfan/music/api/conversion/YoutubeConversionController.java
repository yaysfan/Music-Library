package com.yayfan.music.api.conversion;

import com.yayfan.music.domain.conversion.YoutubeConversionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/conversion")
@RequiredArgsConstructor
public class YoutubeConversionController {

    private final YoutubeConversionService youtubeConversionService;

    @PostMapping
    public void conversion(@Valid @RequestBody YoutubeConversionRequestDto requestDto, Authentication authentication) {
        String username = authentication.getName();
        youtubeConversionService.convertYoutubeUrlToMp3(
                requestDto.getYoutubeUrl(),
                requestDto.getName(),
                requestDto.getGenre(),
                username
        );

    }


}
