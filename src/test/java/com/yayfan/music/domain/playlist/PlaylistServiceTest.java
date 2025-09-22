package com.yayfan.music.domain.playlist;

import com.yayfan.music.domain.user.User;
import com.yayfan.music.domain.user.UserStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void shouldFindMyPlaylists() {
        //given
        when(userStorage.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(playlistStorage.findByUserId(testUser.getId())).thenReturn(List.of(testPlaylist));

        //when
        List<Playlist> myPlaylists = playlistService.findMyPlaylists("testuser");

        //then
        assertEquals(1,myPlaylists.size());
        assertEquals("test",myPlaylists.get(0).getName());
        assertEquals("testuser",myPlaylists.get(0).getUser().getUsername());

        verify(userStorage, times(1)).findByUsername("testuser");
        verify(playlistStorage, times(1)).findByUserId(testUser.getId());
    }

}
