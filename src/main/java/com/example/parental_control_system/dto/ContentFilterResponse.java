// 8. ContentFilterResponse.java
package com.example.parental_control_system.dto;

import lombok.Data;

@Data
public class ContentFilterResponse {
    private Long id;
    private String keyword;
    private boolean active;
}
