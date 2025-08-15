package com.pawpal.backend.controller;

import com.pawpal.backend.dto.AuthenticationRequest;
import com.pawpal.backend.dto.AuthenticationResponse;
import com.pawpal.backend.dto.SignupRequest;
import com.pawpal.backend.dto.UserDto;
import com.pawpal.backend.entity.User;
import com.pawpal.backend.repository.UserRepository;
import com.pawpal.backend.service.auth.AuthService;
import com.pawpal.backend.service.jwt.UserService;
import com.pawpal.backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.createCustomer(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        AuthenticationResponse response = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            response.setJwtToken(jwt);
            response.setUserId(optionalUser.get().getId());
            response.setUserRole(optionalUser.get().getUserRole());
        }
        return response;
    }
}