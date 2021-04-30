package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.UserDTO;
import com.pik.hotpoll.domain.UserWithToken;
import com.pik.hotpoll.exceptions.InvalidAuthenticationException;
import com.pik.hotpoll.services.interfaces.EncryptService;
import com.pik.hotpoll.services.interfaces.JwtService;
import com.pik.hotpoll.services.interfaces.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private UserService userService;
    private EncryptService encryptService;
    private JwtService jwtService;


    @PostMapping("")
    public ResponseEntity userLogin(@Valid @RequestBody LoginParam loginParam) throws InvalidAuthenticationException {
        Optional<User> optional = userService.findByEmail(loginParam.getEmail());
        if (optional.isPresent()
                && encryptService.check(loginParam.getPassword(), optional.get().getPassword())) {
            UserDTO userData = (UserDTO) userService.findById(optional.get().getId());
            return ResponseEntity.ok(
                    userResponse(new UserWithToken(userData, jwtService.toToken(optional.get()))));
        } else {
            throw new InvalidAuthenticationException();
        }
    }


    private Map<String, Object> userResponse(UserWithToken userWithToken) {
        return new HashMap<String, Object>() {
            {
                put("user", userWithToken);
            }
        };
    }
}

@Getter
@JsonRootName("user")
@NoArgsConstructor
class LoginParam {
    private String email;

    private String password;
}