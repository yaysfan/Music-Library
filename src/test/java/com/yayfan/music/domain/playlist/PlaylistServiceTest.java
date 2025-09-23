package com.yayfan.music.domain.playlist;

import com.yayfan.music.domain.user.User;
import com.yayfan.music.domain.user.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {

    @Mock
    private PlaylistStorage playlistStorage;

    @Mock
    private UserStorage userStorage;

    @InjectMocks
    private PlaylistService playlistService;

    private User testUser;
    private Playlist testPlaylist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = User.builder()
                .id(1)
                .username("testuser")
                .password("password")
                .build();

        testPlaylist = Playlist.builder()
                .id(1)
                .name("test")
                .user(testUser)
                .songs(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("자신의 플레이리스트 목록 조회 성공")
    void shouldFindMyPlaylists() {
        //given
        when(userStorage.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(playlistStorage.findByUserId(testUser.getId())).thenReturn(List.of(testPlaylist));

        //when
        List<Playlist> myPlaylists = playlistService.findMyPlaylists("testuser");

        //then
        assertEquals(1, myPlaylists.size());
        assertEquals("test", myPlaylists.get(0).getName());
        assertEquals("testuser", myPlaylists.get(0).getUser().getUsername());

        verify(userStorage, times(1)).findByUsername("testuser");
        verify(playlistStorage, times(1)).findByUserId(testUser.getId());
    }

    @Test
    @DisplayName("자신의 플레이리스트 목록 조회 실패")
    void shouldThrowException_whenUserNotFound() {
        //given
        when(userStorage.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        //when + then
        assertThrows(UsernameNotFoundException.class, () -> {
            playlistService.findMyPlaylists("nonexistentUser");
        });

        verify(playlistStorage, never()).findByUserId(anyInt());

    }

    @Test
    @DisplayName("자신의 플레이리스트 삭제")
    void shouldDeleteMyPlaylist() {
        //given
        when(playlistStorage.findByIdWithSongs(1)).thenReturn(Optional.of(testPlaylist));

        //when
        playlistService.deletePlaylist(1,"testuser");

        //then
        verify(playlistStorage, times(1)).delete(1);
    }
}
