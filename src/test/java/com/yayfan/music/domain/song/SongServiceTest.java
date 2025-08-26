package com.yayfan.music.domain.song;

import com.yayfan.music.api.song.NewSongRequestDto;
import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.file.FileAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SongServiceTest {
    @Mock
    SongStorage songStorage;
    @Mock
    FileAdapter fileAdapter;

    @InjectMocks
    SongService songService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateSong() {
        // Given
        String songName = "Fake Song";
        String genre = "Pop";
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.mp3",
                "audio/mpeg",
                "test content".getBytes()
        );
        Artist artist = Artist.builder()
                .artistName("Artist")
                .id(1)
                .build();
        NewSongRequest dto = new NewSongRequest(songName, genre, multipartFile);

        // When
        when(songStorage.save(any(Song.class))).thenAnswer(i -> i.getArgument(0));
        Song song = songService.createSong(dto, artist);

        // Then
        assertEquals(songName, song.getName());
        assertEquals(genre, song.getGenre());
        assertEquals(artist, song.getArtist());
        verify(fileAdapter, times(1)).save(any(String.class), any(InputStream.class));
    }

    @Test
    public void shouldFindSongById() {
        // Given
        Integer id = 1;

        // When
        when(songStorage.findById(id)).thenReturn(
                Optional.of(
                        Song.builder().id(id).build()
                )
        );

        // Then
        Song song = songService.findById(id);
        assertEquals(id, song.getId());
    }

    @Test
    public void shouldThrowIfSongDoesntExist() {
        // Given
        Integer id = 42;

        // When
        when(songStorage.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(SongNotFoundException.class, () -> songService.findById(id));
    }
}