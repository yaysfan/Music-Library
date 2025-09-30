package com.yayfan.music.integration.conversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class YtDlpAdapter {

    @Value("${config.file.base-path}")
    private String basePath;

    @Value("${config.yt-dlp.path}")
    private String ytDlpPath;

    @Value("${config.yt-dlp.ffmpeg-directory}")
    private String ffmpegDirectory;

    @Value("${config.yt-dlp.cookies-file}")
    private String cookiesFile;

    @Async
    public CompletableFuture<File> convert(String youtubeUrl) {
        Process process = null;
        try {
            String outputFileName = UUID.randomUUID().toString() + ".mp3";
            Path outputPath = Paths.get(basePath, outputFileName);

            // -x : 오디오만 추출
            // --audio-format mp3 : 오디오 포맷을 mp3로 지정
            // -o outputPath : 결과 파일을 저장할 경로와 이름 지정
            // youtubeUrl : 변환할 유튜브 주소
            ProcessBuilder processBuilder = new ProcessBuilder(
                    ytDlpPath,
                    "-4",
                    "--ffmpeg-location", ffmpegDirectory,
                    "--no-playlist",
                    "--cookies", cookiesFile,
                    "--user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36",
                    "-x",
                    "--audio-format", "mp3",
                    "-o", outputPath.toString(),
                    youtubeUrl
            );

            processBuilder.inheritIO();

            log.info("Starting youtube conversion for URL: {}", youtubeUrl);
            process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("Conversion successful for URL: {}. Output file: {}", youtubeUrl, outputFileName);
                return CompletableFuture.completedFuture(outputPath.toFile());
            } else {
                String errorMessage = new String(process.getErrorStream().readAllBytes());
                log.error("Conversion failed for URL: {}. Exit code: {}. Error: {}", youtubeUrl, exitCode, errorMessage);
                return CompletableFuture.failedFuture(new RuntimeException("Youtube conversion failed."));
            }

        } catch (IOException | InterruptedException e) {
            log.error("Exception during youtube conversion", e);
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(new RuntimeException("Exception during youtube conversion.", e));

        } finally {
            if (process != null) {
                process.destroyForcibly();
                log.info("Process for URL {} has been destroyed.", youtubeUrl);
            }
        }
    }
}
