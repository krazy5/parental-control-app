package com.example.parental_control_system.controller;

import com.example.parental_control_system.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/sse")
@RequiredArgsConstructor
public class ActivityStreamController {

    private final Map<Long, SseEmitter> parentConnections = new ConcurrentHashMap<>();

    @GetMapping("/activity-stream")
    public SseEmitter streamActivities(Authentication auth) {
        User parent = (User) auth.getPrincipal();
        SseEmitter emitter = new SseEmitter(30_000L);

        parentConnections.put(parent.getId(), emitter);

        emitter.onCompletion(() -> parentConnections.remove(parent.getId()));
        emitter.onTimeout(() -> parentConnections.remove(parent.getId()));
        emitter.onError((ex) -> parentConnections.remove(parent.getId()));

        return emitter;
    }

    public void broadcastActivity(Long parentId, ActivityEvent activity) {
        SseEmitter emitter = parentConnections.get(parentId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(UUID.randomUUID().toString())
                        .name("activity")
                        .data(activity));
            } catch (IOException e) {
                parentConnections.remove(parentId);
            }
        }
    }
}

