package com.yayfan.music.domain.conversion;

import com.yayfan.music.integration.conversion.YtDlpAdapter;
import com.yayfan.music.integration.notification.SseNotificationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class YoutubeConversionService {

    private final YtDlpAdapter ytDlpAdapter;
    private final SseNotificationAdapter sseNotificationAdapter;

    public void convertYoutubeUrlToMp3(String youtubeUrl, String username) {

        CompletableFuture<File> conversionPromise = ytDlpAdapter.convert(youtubeUrl);

        conversionPromise.whenComplete((file, error) -> {
            if (error != null) {
                sseNotificationAdapter.sendNotification(username, "upload-failed", "File uploaded failed!");
            } else {
                String successMessage = "Conversion successful: " + file.getName();
                sseNotificationAdapter.sendNotification(username, "upload-success", successMessage);
            }
        });


    }
}
