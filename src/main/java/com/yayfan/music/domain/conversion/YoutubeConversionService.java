package com.yayfan.music.domain.conversion;

import com.yayfan.music.api.conversion.YoutubeConversionRequestDto;
import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.artist.ArtistService;
import com.yayfan.music.domain.song.SongService;
import com.yayfan.music.integration.conversion.YtDlpAdapter;
import com.yayfan.music.integration.notification.SseNotificationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class YoutubeConversionService {

    private final YtDlpAdapter ytDlpAdapter;
    private final SseNotificationAdapter sseNotificationAdapter;
    private final ArtistService artistService;
    private final SongService songService;

    public void convertYoutubeUrlToMp3(String youtubeUrl, String name, String genre, String username) {


        CompletableFuture<File> conversionPromise = ytDlpAdapter.convert(youtubeUrl);

        conversionPromise.whenComplete((file, error) -> {

            if (error != null) {
                sseNotificationAdapter.sendNotification(username, "upload-failed", "File uploaded failed!");
            } else {
                String successMessage = "Conversion successful: " + file.getName();
                sseNotificationAdapter.sendNotification(username, "upload-success", successMessage);

                Artist artist = artistService.findByUsername(username);
                songService.saveConvertedSong(name, genre, file.getName(), artist);
            }
        });


    }
}
