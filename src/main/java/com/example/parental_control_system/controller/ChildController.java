package com.example.parental_control_system.controller;

import com.example.parental_control_system.Service.ChildService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/children")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PARENT')")
public class ChildController {

    private final ChildService childService;

    @GetMapping
    public ResponseEntity<List<ChildResponse>> getChildren() {
        return ResponseEntity.ok(childService.getChildrenForCurrentUser());
    }

    @PostMapping
    public ResponseEntity<ChildResponse> addChild(@Valid @RequestBody CreateChildRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(childService.createChild(request));
    }

    @GetMapping("/{childId}/activities")
    public ResponseEntity<List<ActivityResponse>> getChildActivities(@PathVariable Long childId) {
        return ResponseEntity.ok(childService.getChildActivities(childId));
    }
}
