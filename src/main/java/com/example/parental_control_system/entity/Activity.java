package com.example.parental_control_system.entity;

import com.example.parental_control_system.entity.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @Enumerated(EnumType.STRING)
    private ActivityType type; // APP_USAGE, WEB_VISIT, LOCATION_UPDATE, DEVICE_EVENT

    private String description;
    private String appPackage;
    private String url;
    private Integer durationMinutes;

    @Column(columnDefinition = "json")
    private String metadata; // JSON for additional data

    private LocalDateTime timestamp;
}

