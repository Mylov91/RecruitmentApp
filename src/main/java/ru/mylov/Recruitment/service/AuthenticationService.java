package ru.mylov.Recruitment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.mylov.Recruitment.dto.JwtRequest;
import ru.mylov.Recruitment.dto.JwtResponse;
import ru.mylov.Recruitment.dto.RefreshTokenRequest;
import ru.mylov.Recruitment.security.JwtUtil;

import java.util.HashMap;


@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthenticationService(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public JwtResponse authenticate(JwtRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        UserDetails user = userService.loadUserByUsername(request.getUsername());
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), user);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(accessToken);
        jwtResponse.setRefreshToken(refreshToken);
        return jwtResponse;
    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshRequest) {
        String username = jwtUtil.extractUsername(refreshRequest.getToken());
        UserDetails user = userService.loadUserByUsername(username);
        if (jwtUtil.isTokenValid(refreshRequest.getToken(), user)) {
            String jwt = jwtUtil.generateToken(user);
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(jwt);
            jwtResponse.setRefreshToken(refreshRequest.getToken());
            return jwtResponse;
        }
        return null;
    }
}
