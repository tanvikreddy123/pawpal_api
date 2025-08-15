package com.pawpal.backend.service.auth;

import com.pawpal.backend.dto.SignupRequest;
import com.pawpal.backend.dto.UserDto;
import jakarta.annotation.PostConstruct;

public interface AuthService {
    UserDto createCustomer(SignupRequest signupRequest);
    boolean hasUserWithEmail(String email);
    @PostConstruct
    void createAdminAccount();
}