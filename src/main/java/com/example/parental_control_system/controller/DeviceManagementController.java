package com.example.parental_control_system.controller;

import com.example.parental_control_system.Service.DeviceControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PARENT')")
public class DeviceManagementController {

    private final DeviceControlService deviceControlService;
    private final DeviceService deviceService;

    @PostMapping("/{deviceId}/lock")
    public ResponseEntity<Void> lockDevice(@PathVariable String deviceId,
                                           @RequestBody LockDeviceRequest request) {
        deviceControlService.lockDevice(deviceId, request.getReason());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{deviceId}/unlock")
    public ResponseEntity<Void> unlockDevice(@PathVariable String deviceId) {
        deviceControlService.unlockDevice(deviceId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{deviceId}/apps/{appPackage}/limit")
    public ResponseEntity<Void> setAppTimeLimit(@PathVariable String deviceId,
                                                @PathVariable String appPackage,
                                                @RequestBody AppTimeLimitRequest request) {
        deviceControlService.setAppTimeLimit(deviceId, appPackage, request.getLimitMinutes());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{deviceId}/status")
    public ResponseEntity<DeviceStatusResponse> getDeviceStatus(@PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceStatus(deviceId));
    }
}

