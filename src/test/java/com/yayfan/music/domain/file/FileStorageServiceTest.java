package com.yayfan.music.domain.file;

import com.yayfan.music.domain.song.SongStorage;
import com.yayfan.music.notification.NotificationService;
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

    // 1. 가짜(Mock) 객체 만들기
    @Mock
    private FileAdapter fileAdapter; // 가짜 파일 저장소

    @Mock
    private NotificationService notificationService; // 가짜 알림 서비스

    @Mock
    private SongStorage songStorage; // 가짜 DB 저장소

    // 2. 테스트 대상 객체에 가짜 객체들 주입하기
    @InjectMocks
    private FileStorageService fileStorageService;

    // 3. 각 테스트가 시작되기 전에 Mockito를 초기화
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldDeleteSongFromDbWhenFileSaveFails() throws IOException {
        // --- Given (준비) ---
        String username = "testuser";
        Integer songId = 1;
        String fileName = "test.mp3";
        InputStream inputStream = new MockMultipartFile("test", new byte[0]).getInputStream();

        // 4. "가짜 실패 상황" 설정: fileAdapter.save가 호출되면, 일부러 예외를 던져라!
        doThrow(new FileAdapterException("Disk full", null))
                .when(fileAdapter).save(any(String.class), any(InputStream.class));

        // --- When (실행) ---
        // 파일 저장을 시도 (내부적으로는 예외가 발생할 것임)
        fileStorageService.saveSongFile(username, songId, fileName, inputStream);

        // --- Then (검증) ---
        // 5. '뒷정리'가 잘 되었는지 확인: songStorage.deleteById가 'songId'와 함께 호출되었는가?
        verify(songStorage).deleteById(eq(songId));

        // 6. 사용자에게 실패 알림을 잘 보냈는지 확인
        verify(notificationService).sendNotification(eq(username), eq("upload-fail"), any(String.class));
    }
}