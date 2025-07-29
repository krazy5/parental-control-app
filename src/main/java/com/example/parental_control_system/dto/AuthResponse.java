// 3. AuthResponse.java
package com.example.parental_control_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
}
