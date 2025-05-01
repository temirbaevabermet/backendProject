package com.example.OnlineCosmeticStore.Security;

import com.example.OnlineCosmeticStore.Entity.Role;
import com.example.OnlineCosmeticStore.Entity.User;
import com.example.OnlineCosmeticStore.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        if (!userRepository.existsByUsername("testuser")) {
            User user = User.builder()
                    .email("test@example.com")
                    .username("testuser")
                    .password(passwordEncoder.encode("testpass"))
                    .enabled(true)
                    .role(Role.CUSTOMER)
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void loginWithValidCredentialsShouldReturnAccessToken() throws Exception {
        String json = """
            {
                "username": "testuser",
                "password": "testpass"
            }
        """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void loginWithInvalidPasswordShouldFail() throws Exception {
        String json = """
            {
                "username": "testuser",
                "password": "wrongpass"
            }
        """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerNewUserShouldSucceed() throws Exception {
        String json = """
            {
                "username": "newuser",
                "email": "new@example.com",
                "password": "newpass123"
            }
        """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User registered successfully")));
    }
}
