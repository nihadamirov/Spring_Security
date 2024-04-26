package edu.company.dto;

public record AuthRequest(
        String username,
        String email,
        String password
) {
}
