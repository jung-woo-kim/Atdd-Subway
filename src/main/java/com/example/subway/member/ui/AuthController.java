package com.example.subway.member.ui;

import com.example.subway.member.application.AuthService;
import com.example.subway.member.application.dto.TokenRequest;
import com.example.subway.member.application.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authService.login(tokenRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @GetMapping("/login/oauth2/code")
    public ResponseEntity<TokenResponse> getUserInfo(@RequestParam String code) {
        TokenResponse tokenResponse = authService.loginWithGithub(code);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
