package com.pik.hotpoll.controllers;

import javax.validation.Valid;

import com.pik.hotpoll.config.JwtUtils;
import com.pik.hotpoll.config.UserDetailsImpl;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.payload.JwtResponse;
import com.pik.hotpoll.payload.LoginRequest;
import com.pik.hotpoll.payload.MessageResponse;
import com.pik.hotpoll.payload.SignupRequest;

import com.pik.hotpoll.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/singin")
    public ResponseEntity<?> singinGet() {

        return ResponseEntity.ok("singin");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(JwtResponse.builder()
                        .jwt(jwt)
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail()).build());
    }

    @GetMapping("/singup")
    public ResponseEntity<?> singupGet() {

        return ResponseEntity.ok("singup");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws ConstraintsViolationException {
        if (userService.findByNickname(signUpRequest.getUsername()).size() != 0) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder().message("Error: Username is already taken!").build());
        }

        if (userService.findByEmail(signUpRequest.getEmail()).size() != 0) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder().message("Error: Email is already in use!").build());
        }

        // Create new user's account
        userService.create(User.builder().nickname(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword())).build());


        return ResponseEntity.ok(MessageResponse.builder().message("User registered successfully!").build());
    }


}
