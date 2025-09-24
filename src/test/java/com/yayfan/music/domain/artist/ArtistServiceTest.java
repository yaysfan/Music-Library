package com.yayfan.music.domain.artist;

import com.yayfan.music.domain.song.Song;
import com.yayfan.music.domain.song.SongService;
import com.yayfan.music.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ArtistService 클래스")
class ArtistServiceTest {

    @Mock
    private ArtistStorage artistStorage;
    @Mock
    private SongService songService;

    @InjectMocks
    private ArtistService artistService;

    private User testUser;
    private Artist testArtist;
    private Song testSong1;
    private Song testSong2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = User.builder().id(1).username("testartist").build();

        testSong1 = Song.builder().id(101).name("Song 1").build();
        testSong2 = Song.builder().id(102).name("Song 2").build();

        testArtist = Artist.builder()
                .id(10)
                .artistName("Test Artist")
                .user(testUser)
                .songs(List.of(testSong1, testSong2))
                .build();
    }

    @Nested
    @DisplayName("findByUsername 메소드는")
    class FindByUsernameTests {

        @Test
        @DisplayName("존재하는 유저 이름으로 아티스트를 성공적으로 조회한다")
        void findByUsername_Success() {
            //given
            String username = testUser.getUsername();
            when(artistStorage.findByUsername(username)).thenReturn(Optional.of(testArtist));

            //when
            Artist foundArtist = artistService.findByUsername(username);

            //then
            assertNotNull(foundArtist);
            assertEquals(testArtist.getArtistName(), foundArtist.getArtistName());
            verify(artistStorage, times(1)).findByUsername(username);
        }

        @Test
        @DisplayName("존재하지 않는 유저 이름으로 조회 시 ArtistNotFoundException을 던진다")
        void findByUsername_NotFound() {
            //given
            String nonExistentUsername = "ghost";
            when(artistStorage.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

            //when & then
            assertThrows(ArtistNotFoundException.class, () -> {
                artistService.findByUsername(nonExistentUsername);
            });
        }
    }

    @Nested
    @DisplayName("deleteArtist 메소드는")
    class DeleteArtistTests {

        @Test
        @DisplayName("아티스트와 그 아티스트의 모든 노래를 성공적으로 삭제한다")
        void deleteArtist_Success() {
            //given
            Integer artistId = testArtist.getId();
            when(artistStorage.findById(artistId)).thenReturn(Optional.of(testArtist));

            //when
            artistService.deleteArtist(artistId);

            //then
            verify(songService, times(2)).deleteSong(any(Song.class));
            verify(songService, times(1)).deleteSong(testSong1);
            verify(songService, times(1)).deleteSong(testSong2);

            verify(artistStorage, times(1)).deleteArtist(artistId);
        }

        @Test
        @DisplayName("삭제할 아티스트가 존재하지 않으면 ArtistNotFoundException을 던진다")
        void deleteArtist_NotFound() {
            //given
            Integer nonExistentArtistId = 999;
            when(artistStorage.findById(nonExistentArtistId)).thenReturn(Optional.empty());

            //when & then
            assertThrows(ArtistNotFoundException.class, () -> {
                artistService.deleteArtist(nonExistentArtistId);
            });

            verify(songService, never()).deleteSong(any());
            verify(artistStorage, never()).deleteArtist(any());
        }
    }
}