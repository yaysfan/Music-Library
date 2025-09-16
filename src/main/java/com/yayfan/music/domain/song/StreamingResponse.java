package com.yayfan.music.domain.song;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public record StreamingResponse(
        ResourceRegion region,
        HttpStatus status,
        HttpHeaders headers
) {
}