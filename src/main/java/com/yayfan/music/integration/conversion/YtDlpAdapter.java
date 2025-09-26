package com.yayfan.music.integration.conversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j // 로그를 찍기 위해 추가
public class YtDlpAdapter {

    // 나중에 파일이 저장될 경로를 설정 파일에서 받아올 수 있습니다.
    @Value("${config.file.base-path}")
    private String basePath;

    public File convert(String youtubeUrl) {
        try {
            // 1. 고유한 파일 이름을 생성합니다. (UUID 사용)
            String outputFileName = UUID.randomUUID().toString() + ".mp3";
            Path outputPath = Paths.get(basePath, outputFileName);

            // 2. yt-dlp 명령어 준비
            // -x : 오디오만 추출
            // --audio-format mp3 : 오디오 포맷을 mp3로 지정
            // -o outputPath : 결과 파일을 저장할 경로와 이름 지정
            // youtubeUrl : 변환할 유튜브 주소
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "yt-dlp",
                    "-x",
                    "--audio-format", "mp3",
                    "-o", outputPath.toString(),
                    youtubeUrl
            );

            // 3. 명령어 실행
            log.info("Starting youtube conversion for URL: {}", youtubeUrl);
            Process process = processBuilder.start();

            // 4. 프로세스가 끝날 때까지 기다림 (0 = 성공)
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("Conversion successful for URL: {}. Output file: {}", youtubeUrl, outputFileName);
                return outputPath.toFile();
            } else {
                // 에러 스트림을 읽어서 로그에 남기면 디버깅에 도움이 됩니다.
                String errorMessage = new String(process.getErrorStream().readAllBytes());
                log.error("Conversion failed for URL: {}. Exit code: {}. Error: {}", youtubeUrl, exitCode, errorMessage);
                throw new RuntimeException("Youtube conversion failed.");
            }

        } catch (IOException | InterruptedException e) {
            log.error("Exception during youtube conversion", e);
            Thread.currentThread().interrupt(); // InterruptedException 처리
            throw new RuntimeException("Exception during youtube conversion.", e);
        }
    }
}
