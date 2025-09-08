package com.yayfan.music.web.song;

import com.yayfan.music.api.song.NewSongRequestDto;
import com.yayfan.music.api.song.SongMapper;
import com.yayfan.music.domain.artist.Artist;
import com.yayfan.music.domain.artist.ArtistService;
import com.yayfan.music.domain.song.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SongWebController {

    private final SongService songService;
    private final ArtistService artistService;
    private final SongMapper songMapper;

    @GetMapping("/songs")
    public String getSongPage(Model model, Authentication authentication) {
        System.out.println(authentication);
        String name = authentication.getName();
        model.addAttribute("songs", songService.findSongsByArtistName(name));
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
