package edu.company.dto;

import edu.company.model.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateUserRequest(
        String name,
        String username,
        String email,
        String password,
        Set<Role> authorities
) {

}
