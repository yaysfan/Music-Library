package com.yayfan.music.domain.song;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public record StreamingResponse(
        Resource resource,
        HttpStatus status,
        HttpHeaders headers
) {
}