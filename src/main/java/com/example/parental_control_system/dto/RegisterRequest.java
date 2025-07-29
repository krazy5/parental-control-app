// 4. RegisterRequest.java
package com.example.parental_control_system.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String role; // PARENT or CHILD
}
