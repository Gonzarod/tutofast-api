package com.evertix.tutofastapi.service;

import com.evertix.tutofastapi.security.request.LoginRequest;
import com.evertix.tutofastapi.security.request.SignUpRequest;
import com.evertix.tutofastapi.security.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);
    JwtResponse authenticateUser(LoginRequest loginRequest);
}
