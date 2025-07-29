package com.example.parental_control_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/screen-time")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PARENT')")
public class ScreenTimeController {

    private final ScreenTimeService screenTimeService;

    @PostMapping("/rules/child/{childId}")
    public ResponseEntity<ScreenTimeRuleResponse> createRule(
            @PathVariable Long childId,
            @Valid @RequestBody CreateScreenTimeRuleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(screenTimeService.createRule(childId, request));
    }

    @GetMapping("/usage/child/{childId}")
    public ResponseEntity<ScreenTimeUsageResponse> getUsage(
            @PathVariable Long childId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(screenTimeService.getUsage(childId, date));
    }

    @PostMapping("/emergency-unlock/{deviceId}")
    public ResponseEntity<Void> emergencyUnlock(@PathVariable String deviceId) {
        screenTimeService.emergencyUnlock(deviceId);
        return ResponseEntity.ok().build();
    }
}
