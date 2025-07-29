// 6. AuthService.java
package com.example.parental_control_system.Service;

import com.example.parental_control_system.dto.AuthResponse;
import com.example.parental_control_system.dto.LoginRequest;
import com.example.parental_control_system.dto.RegisterRequest;
import com.example.parental_control_system.entity.User;
import com.example.parental_control_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        User user = (User) auth.getPrincipal();
        String token = tokenProvider.generateToken(auth);
        return new AuthResponse(token, "Bearer", user.getId(), user.getUsername());
    }

    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username taken");
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        userRepo.save(user);
    }
}
