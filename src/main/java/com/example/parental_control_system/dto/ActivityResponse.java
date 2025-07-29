// 7. ActivityResponse.java
package com.example.parental_control_system.dto;

import com.example.parental_control_system.entity.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ActivityResponse {
    private Long id;
    private ActivityType type;
    private String details;
    private LocalDateTime timestamp;
}
