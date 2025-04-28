package com.example.OnlineCosmeticStore.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider; // For social authorization (Google, Facebook, GitHub)

}
