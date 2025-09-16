package com.yayfan.music.notification;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String username) { // userId -> username
        SseEmitter emitter = new SseEmitter(10 * 60 * 1000L);
        emitters.put(username, emitter); // userId -> username

        emitter.onCompletion(() -> emitters.remove(username)); // userId -> username
        emitter.onTimeout(() -> emitters.remove(username)); // userId -> username

        try {
            emitter.send(SseEmitter.event().name("connect").data("Connection successful"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    public void sendNotification(String username, String eventName, Object data) { // userId -> username
        SseEmitter emitter = emitters.get(username); // userId -> username
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (IOException e) {
                emitters.remove(username); // userId -> username
            }
        }
    }
}