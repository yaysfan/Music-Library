package com.yayfan.music.api.song;

import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.artist.ArtistService;
import com.yayfan.music.domain.song.Song;
import com.yayfan.music.domain.song.SongService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SongWebController {

    private final SongService songService;
    private final ArtistService artistService;
    private final SongMapper songMapper;

    @GetMapping("/songs")
    public String getSongPage(Model model) {
        List<Song> songs = songService.searchSongs("");
        model.addAttribute("songs", songs);
        return "songs";
    }

    @GetMapping("/songs/upload")
    public String getUploadPage() {
        return "upload-form";
    }

    @PostMapping("/songs/upload")
    public String uploadSong(NewSongRequestDto request, Authentication authentication) {
        Artist artist = artistService.findByUsername(authentication.getName());
        songService.createSong(songMapper.toNewSongRequest(request), artist);
        return "redirect:/songs";
    }
}
