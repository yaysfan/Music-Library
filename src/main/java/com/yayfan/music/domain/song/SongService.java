package com.yayfan.music.domain.song;

import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.file.FileAdapter;
import com.yayfan.music.domain.file.FileAdapterException;
import com.yayfan.music.domain.file.InvalidFileTypeException;
import com.yayfan.music.domain.playlist.PlaylistStorage;
import com.yayfan.music.persistence.playlist.PlaylistStore;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class SongService {
    private final SongStorage songStorage;
    private final FileAdapter fileAdapter;
    private final PlaylistStorage playlistStorage;
    public static final String AUDIO_MPEG = "audio/mpeg";

    @Transactional
    public Song createSong(NewSongRequest request, Artist artist)
            throws FileAdapterException, InvalidFileTypeException {
        if (!Objects.equals(request.getFile().getContentType(), AUDIO_MPEG)) {
            throw new InvalidFileTypeException("File must be a mp3 file");
        }

        String fileName = UUID.randomUUID() + ".mp3";
        fileAdapter.save(fileName, fileAdapter.getStream(request.getFile()));
        Song song = Song.builder()
                .name(request.getName())
                .genre(request.getGenre())
                .file(fileName)
                .artist(artist)
                .build();

        return songStorage.save(song);
    }

    public Song findById(Integer id) throws SongNotFoundException {
        return songStorage.findById(id)
                .orElseThrow(SongNotFoundException::new);
    }

    public InputStream loadSong(String fileName) throws FileAdapterException {
        return fileAdapter.load(fileName);
    }

    @Transactional
    public void deleteSong(Song song) {
        fileAdapter.delete(song.getFile());
        songStorage.deleteById(song.getId());
    }


    @Transactional
    public void deleteSongById(Integer songId, String username) {
        Song song = songStorage.findById(songId)
                .orElseThrow(SongNotFoundException::new);

        if (!song.getArtist().getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not the owner of this song");
        }

        fileAdapter.delete(song.getFile()); //개선 고민 필요

        songStorage.deleteById(songId);
    }

    public List<Song> searchSongs(String search) {
        return songStorage.search(search);
    }

    public List<Song> findSongsByArtistId(Integer artistId) {
        return songStorage.findByArtistId(artistId);
    }

    public StreamingResponse prepareStreaming(Integer songId, String rangeHeader) throws IOException {
        Song song = findById(songId);

        Resource resource = fileAdapter.loadAsResource(song.getFile());
        long fileLength = resource.contentLength();

        List<HttpRange> ranges = HttpRange.parseRanges(rangeHeader);
        HttpHeaders headers = new HttpHeaders();

        if (ranges.isEmpty()) {
            headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength));
            return new StreamingResponse(resource, HttpStatus.OK, headers);
        } else {
            HttpRange range = ranges.get(0);
            long start = range.getRangeStart(fileLength);
            long end = range.getRangeEnd(fileLength);
            long contentLength = (end - start) + 1;

            String contentRange = "bytes " + start + "-" + end + "/" + fileLength;
            headers.add(HttpHeaders.CONTENT_RANGE, contentRange);
            headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
            headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");

            return new StreamingResponse(resource, HttpStatus.PARTIAL_CONTENT, headers);
        }
    }



}
