package com.yayfan.music.configuration;

import com.yayfan.music.domain.conversion.YoutubeConversionService;
import com.yayfan.music.integration.conversion.YtDlpAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class YtDlpConfig {

    @Bean
    public YtDlpAdapter ytDlpAdapter() {
        return new YtDlpAdapter();
    }

    @Bean
    public YoutubeConversionService youtubeConversionService(
            YtDlpAdapter ytDlpAdapter,
            com.yayfan.music.integration.notification.SseNotificationAdapter sseNotificationAdapter,
            com.yayfan.music.domain.artist.ArtistService artistService,
            com.yayfan.music.domain.song.SongService songService) {
        return new YoutubeConversionService(ytDlpAdapter, sseNotificationAdapter, artistService, songService);
    }
}