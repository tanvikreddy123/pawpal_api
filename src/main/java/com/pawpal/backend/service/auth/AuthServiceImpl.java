package com.pawpal.backend.service.auth;

import com.pawpal.backend.dto.SignupRequest;
import com.pawpal.backend.dto.UserDto;
import com.pawpal.backend.entity.User;
import com.pawpal.backend.enums.UserRole;
import com.pawpal.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount == null) {
            User newAdmin = new User();
            newAdmin.setName("Admin");
            newAdmin.setEmail("admin@pawpal.com");
            newAdmin.setPassword(passwordEncoder.encode("admin"));
            newAdmin.setUserRole(UserRole.ADMIN);
            userRepository.save(newAdmin);
            System.out.println("Admin account created successfully!");
        }
    }

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.USER);
        User createdUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}