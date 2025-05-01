package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Entity.RefreshToken;
import com.example.OnlineCosmeticStore.Entity.Role;
import com.example.OnlineCosmeticStore.Entity.User;
import com.example.OnlineCosmeticStore.Repository.RefreshTokenRepository;
import com.example.OnlineCosmeticStore.Repository.UserRepository;
import com.example.OnlineCosmeticStore.Security.JwtUtils;
import com.example.OnlineCosmeticStore.Service.RefreshTokenService;
import com.example.OnlineCosmeticStore.dto.LoginRequest;
import com.example.OnlineCosmeticStore.dto.RegisterRequest;
import com.example.OnlineCosmeticStore.dto.TokenRefreshRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Authentication", description = "Handles user login, registration and token refresh")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Operation(summary = "User registration", description = "Register a new user")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .enabled(true) // Нужно подтвердить почту
                .build();
        userRepository.save(user);

        // TODO: отправить email верификацию

        return ResponseEntity.ok("User registered successfully. Please verify your email.");
    }

    @Operation(summary = "User login", description = "Authenticate and return JWT tokens")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        String accessToken = jwtUtils.generateJwtToken(user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken.getToken());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Refresh access token", description = "Use refresh token to get new access token")
    @ApiResponse(responseCode = "200", description = "Token refreshed")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateJwtToken(user.getEmail());
                    Map<String, String> response = new HashMap<>();
                    response.put("accessToken", token);
                    response.put("refreshToken", requestRefreshToken);
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}