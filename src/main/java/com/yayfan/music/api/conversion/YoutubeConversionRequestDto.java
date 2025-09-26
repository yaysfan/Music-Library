package com.yayfan.music.api.conversion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

@Value
public class YoutubeConversionRequestDto {

    @NotBlank(message = "URL은 비어있을 수 없습니다.")
    @URL(message = "올바른 URL 형식이 아닙니다.")
    String youtubeUrl;

    @JsonCreator
    public YoutubeConversionRequestDto(@JsonProperty("youtubeUrl") String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}