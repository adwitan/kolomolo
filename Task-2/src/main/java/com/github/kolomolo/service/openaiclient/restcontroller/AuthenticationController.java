package com.github.kolomolo.service.openaiclient.restcontroller;


import com.github.kolomolo.service.openaiclient.model.db.User;
import com.github.kolomolo.service.openaiclient.model.response.AuthenticationResponse;
import com.github.kolomolo.service.openaiclient.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request ){
        return ResponseEntity.ok(authenticationService.register(request));
    }
}
