package com.yayfan.music.domain.file;

import com.yayfan.music.domain.song.SongStorage;
import com.yayfan.music.integration.notification.SseNotificationAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class FileStorageServiceTest {

    @Mock
    private FileAdapter fileAdapter;

    @Mock
    private SseNotificationAdapter sseNotificationAdapter;

    @Mock
    private SongStorage songStorage;

    @InjectMocks
    private FileStorageService fileStorageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldDeleteSongFromDbWhenFileSaveFails() throws IOException {
        //given
        String username = "testuser";
        Integer songId = 1;
        String fileName = "test.mp3";
        InputStream inputStream = new MockMultipartFile("test", new byte[0]).getInputStream();

        doThrow(new FileAdapterException("Disk full", null))
                .when(fileAdapter).save(any(String.class), any(InputStream.class));

        //when
        fileStorageService.saveSongFile(username, songId, fileName, inputStream);

        //then
        verify(songStorage).deleteById(eq(songId));
        verify(sseNotificationAdapter).sendNotification(eq(username), eq("upload-fail"), any(String.class));
    }
}