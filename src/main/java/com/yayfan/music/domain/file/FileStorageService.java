package com.yayfan.music.domain.file;

import com.yayfan.music.domain.song.SongStorage;
import com.yayfan.music.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileAdapter fileAdapter;
    private final NotificationService notificationService;
    private final SongStorage songStorage;

    @Async
    public void saveSongFile(String username,Integer songId, String fileName, InputStream inputStream) {
        try {
            fileAdapter.save(fileName, inputStream);

            notificationService.sendNotification(username, "upload-success", "File uploaded successfully!");

        } catch (FileAdapterException e) {
            notificationService.sendNotification(username, "upload-fail", "File upload failed: " + e.getMessage());
            songStorage.deleteById(songId);
        }
    }
}