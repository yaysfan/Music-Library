package com.yayfan.music.domain.song;

import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.file.FileAdapter;
import com.yayfan.music.domain.file.FileStorageService;
import com.yayfan.music.domain.file.InvalidFileTypeException;
import com.yayfan.music.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("SongService 클래스")
class SongServiceTest {

    @Mock
    private SongStorage songStorage;
    @Mock
    private FileAdapter fileAdapter;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private Cache cache;

    @InjectMocks
    private SongService songService;

    private User testUser;
    private Artist testArtist;
    private Song testSong;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = User.builder()
                .id(1)
                .username("testuser")
                .password("password")
                .build();

        testArtist = Artist.builder()
                .id(10)
                .artistName("Test Artist")
                .user(testUser)
                .build();

        testSong = Song.builder()
                .id(100)
                .name("Test Song")
                .genre("Pop")
                .file("testfile.mp3")
                .artist(testArtist)
                .build();

        when(cacheManager.getCache(anyString())).thenReturn(cache);
    }

    @Nested
    @DisplayName("createSong 메소드는")
    class CreateSongTests {

        @Test
        @DisplayName("올바른 mp3 파일로 노래를 성공적으로 생성한다")
        void createSong_Success() throws IOException {
            //given
            MultipartFile mp3File = new MockMultipartFile(
                    "file", "test.mp3", "audio/mpeg", "test content".getBytes()
            );
            NewSongRequest request = new NewSongRequest("New Song", "Rock", mp3File);

            when(songStorage.save(any(Song.class))).thenReturn(testSong);
            when(fileAdapter.getStream(any())).thenReturn(InputStream.nullInputStream());

            //when
            Song createdSong = songService.createSong(request, testArtist);

            //then
            assertNotNull(createdSong);
            assertEquals(testSong.getId(), createdSong.getId());

            verify(songStorage, times(1)).save(any(Song.class));
            verify(fileStorageService, times(1)).saveSongFile(
                    eq(testArtist.getUser().getUsername()),
                    eq(createdSong.getId()),
                    anyString(),
                    any(InputStream.class)
            );
        }

        @Test
        @DisplayName("mp3가 아닌 파일 타입으로 생성 시 InvalidFileTypeException을 던진다")
        void createSong_InvalidFileType() {
            //given
            MultipartFile notMp3File = new MockMultipartFile(
                    "file", "test.txt", "text/plain", "test content".getBytes()
            );
            NewSongRequest request = new NewSongRequest("New Song", "Rock", notMp3File);

            //when & then
            assertThrows(InvalidFileTypeException.class, () -> {
                songService.createSong(request, testArtist);
            });

            verify(songStorage, never()).save(any());
            verify(fileStorageService, never()).saveSongFile(any(), any(), any(), any());
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class FindByIdTests {
        @Test
        @DisplayName("존재하는 ID로 노래를 성공적으로 조회한다")
        void findById_Success() {
            //given
            Integer songId = testSong.getId();
            when(songStorage.findById(songId)).thenReturn(Optional.of(testSong));

            //when
            Song foundSong = songService.findById(songId);

            //then
            assertNotNull(foundSong);
            assertEquals(songId, foundSong.getId());
        }

        @Test
        @DisplayName("존재하지 않는 ID로 조회 시 SongNotFoundException을 던진다")
        void findById_NotFound() {
            //given
            Integer nonExistentId = 999;
            when(songStorage.findById(nonExistentId)).thenReturn(Optional.empty());

            //when & then
            assertThrows(SongNotFoundException.class, () -> {
                songService.findById(nonExistentId);
            });
        }
    }

    @Nested
    @DisplayName("deleteSongById 메소드는")
    class DeleteSongByIdTests {

        @Test
        @DisplayName("자신의 노래를 성공적으로 삭제한다")
        void deleteSongById_Success() {
            //given
            Integer songId = testSong.getId();
            String ownerUsername = testUser.getUsername();
            when(songStorage.findById(songId)).thenReturn(Optional.of(testSong));

            //when
            songService.deleteSongById(songId, ownerUsername);

            //then
            verify(fileAdapter, times(1)).delete(testSong.getFile());
            verify(songStorage, times(1)).deleteById(songId);
        }

        @Test
        @DisplayName("다른 사람의 노래 삭제 시 AccessDeniedException을 던진다")
        void deleteSongById_AccessDenied() {
            //given
            Integer songId = testSong.getId();
            String otherUsername = "hacker";
            when(songStorage.findById(songId)).thenReturn(Optional.of(testSong));

            //when & then
            assertThrows(AccessDeniedException.class, () -> {
                songService.deleteSongById(songId, otherUsername);
            });

            verify(fileAdapter, never()).delete(any());
            verify(songStorage, never()).deleteById(any());
        }
    }
}