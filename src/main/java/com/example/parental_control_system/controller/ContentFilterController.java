package com.example.parental_control_system.controller;

import com.example.parental_control_system.Service.ContentFilterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/content-filters")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PARENT')")
public class ContentFilterController {

    private final ContentFilterService contentFilterService;

    @PostMapping("/child/{childId}")
    public ResponseEntity<ContentFilterResponse> addFilter(
            @PathVariable Long childId,
            @Valid @RequestBody CreateContentFilterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contentFilterService.createFilter(childId, request));
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<ContentFilterResponse>> getChildFilters(
            @PathVariable Long childId) {
        return ResponseEntity.ok(contentFilterService.getChildFilters(childId));
    }

    @PutMapping("/{filterId}/toggle")
    public ResponseEntity<Void> toggleFilter(@PathVariable Long filterId) {
        contentFilterService.toggleFilter(filterId);
        return ResponseEntity.ok().build();
    }
}
