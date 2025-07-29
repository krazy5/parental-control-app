// 12. ParentNotification.java
package com.example.parental_control_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "parent_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "parent_id")
    private User parent;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;

    @Convert(converter = JpaJsonConverter.class)
    private Map<String, Object> metadata;

    private LocalDateTime createdAt;
}
