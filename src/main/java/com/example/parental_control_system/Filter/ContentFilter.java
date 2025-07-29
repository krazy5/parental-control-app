package com.example.parental_control_system.Filter;

import com.example.parental_control_system.Service.ContentFilterService;
import com.example.parental_control_system.entity.Child;
import jakarta.persistence.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;

@Entity
@Table(name = "content_filters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Enumerated(EnumType.STRING)
    private FilterType type; // WEBSITE, KEYWORD, CATEGORY

    private String value; // URL, keyword, or category name

    @Enumerated(EnumType.STRING)
    private EventFilter.FilterAction action; // BLOCK, ALLOW, WARN

    private Boolean isActive = true;
    private LocalDateTime createdAt;

}
