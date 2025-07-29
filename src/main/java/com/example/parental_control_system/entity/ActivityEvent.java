// 2. ActivityEvent.java
package com.example.parental_control_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    private String details; // e.g., app package or URL

    private LocalDateTime timestamp;
}
