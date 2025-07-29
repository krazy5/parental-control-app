package com.example.parental_control_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_commands")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @Enumerated(EnumType.STRING)
    private CommandType type; // LOCK, UNLOCK, RESTART, INSTALL_APP, UNINSTALL_APP

    private String parameters; // JSON string for command parameters

    @Enumerated(EnumType.STRING)
    private CommandStatus status; // PENDING, SENT, ACKNOWLEDGED, COMPLETED, FAILED

    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
    private String result;
}

