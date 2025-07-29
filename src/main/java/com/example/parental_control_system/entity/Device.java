package com.example.parental_control_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String deviceId; // Unique identifier for the device

    private String deviceName;
    private String deviceType; // ANDROID.
    private String osVersion;
    private String manufacturer;
    private String model;

    @Column(nullable = false)
    private Long parentId; // Foreign key to parent/user

    @Column(nullable = false)
    private Long childId; // Foreign key to child/user being monitored

    @Enumerated(EnumType.STRING)
    private DeviceStatus status; // ACTIVE, INACTIVE, BLOCKED, PENDING

    private LocalDateTime registeredAt;
    private LocalDateTime lastSeenAt;
    private String ipAddress;
    private String macAddress;

    // Bidirectional relationship with DeviceCommand
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DeviceCommand> deviceCommands;

    // Additional fields for parental control
    private Boolean locationTrackingEnabled = true;
    private Boolean screenTimeEnabled = true;
    private Boolean appBlockingEnabled = true;
    private Boolean webFilteringEnabled = true;
}
