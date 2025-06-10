package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(of = {"email"})
public @Data class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Instant registrationDate;
}
