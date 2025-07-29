package com.example.parental_control_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "screen_time_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenTimeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    private Integer dailyLimitMinutes;
    private LocalTime allowedStartTime;
    private LocalTime allowedEndTime;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek; // null for all days

    private Boolean isActive = true;
}
