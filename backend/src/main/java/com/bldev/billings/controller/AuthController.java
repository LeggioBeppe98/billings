package com.bldev.billings.controller;

import com.bldev.billings.dto.LoginRequest;
import com.bldev.billings.dto.LoginResponse;
import com.bldev.billings.dto.RefreshRequest;
import com.bldev.billings.exception.UnauthorizedException;
import com.bldev.billings.security.JwtService;
import com.bldev.billings.security.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        // 1. Autenticazione email + password
        var authToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );
        authenticationManager.authenticate(authToken);

        // 2. Carico l'utente
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

        // 3. Genero l'accessToken
        String accessToken = jwtService.generateAccessToken(userDetails);

        // 4. Genero il refresh token
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // 4. Lo restituisco
        return ResponseEntity.ok(new LoginResponse(accessToken,refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // In un sistema stateless non c'è molto da fare qui.
        // Puoi aggiungere logging o futura blacklist.
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {

        String refreshToken = request.refreshToken();
        String username = jwtService.extractUsername(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new UnauthorizedException();
        }

        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(newAccessToken, refreshToken));
    }

}