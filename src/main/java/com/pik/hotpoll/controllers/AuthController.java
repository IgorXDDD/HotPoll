package com.pik.hotpoll.controllers;

import com.pik.hotpoll.config.JwtUtils;
import com.pik.hotpoll.config.RESTAuthenticationEntryPoint;
import com.pik.hotpoll.config.SecurityConfig;
import com.pik.hotpoll.config.UserDetailsImpl;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.payloads.JwtResponse;
import com.pik.hotpoll.payloads.LoginRequest;
import com.pik.hotpoll.payloads.MessageResponse;
import com.pik.hotpoll.payloads.SignupRequest;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.services.DefaultUserService;
import com.pik.hotpoll.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("")
@ComponentScan(basePackageClasses = DefaultUserService.class)
public class AuthController {

    AuthenticationManager authenticationManager;


    PasswordEncoder encoder;


    UserService userService;


    JwtUtils jwtUtils;

    @Autowired
    AuthController(AuthenticationManager authenticationManager,
                   PasswordEncoder encoder,
                   DefaultUserService userService,
                   JwtUtils jwtUtils){
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.userService = userService;
        this.jwtUtils =jwtUtils;

    }

    @GetMapping("/signin")
    public ResponseEntity<?> authenticateUser() {

        return ResponseEntity.ok("s");
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws ConstraintsViolationException {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = userService.create(signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                signUpRequest.getEmail());


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}