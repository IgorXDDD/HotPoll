package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.hotpoll.domain.AuthProvider;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.UserDTO;
import com.pik.hotpoll.domain.UserWithToken;
import com.pik.hotpoll.exceptions.BadRequestException;
import com.pik.hotpoll.exceptions.InvalidAuthenticationException;
import com.pik.hotpoll.payload.ApiResponse;
import com.pik.hotpoll.payload.AuthResponse;
import com.pik.hotpoll.payload.LoginRequest;
import com.pik.hotpoll.payload.SignUpRequest;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.security.TokenProvider;
import com.pik.hotpoll.security.AuthenticationManager;
import com.pik.hotpoll.services.interfaces.EncryptService;
import com.pik.hotpoll.services.interfaces.JwtService;
import com.pik.hotpoll.services.interfaces.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;

@RestController

@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        UserDTO user = new UserDTO();
        user.setUsername(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ObjectMapper objectMapper = new ObjectMapper();

        User result = userRepository.save(objectMapper.convertValue(user, User.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }
}
