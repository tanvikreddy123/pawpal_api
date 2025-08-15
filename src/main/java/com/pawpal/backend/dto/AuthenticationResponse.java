package com.pawpal.backend.dto;

import com.pawpal.backend.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwtToken;
    private UserRole userRole;
    private Long userId;
}