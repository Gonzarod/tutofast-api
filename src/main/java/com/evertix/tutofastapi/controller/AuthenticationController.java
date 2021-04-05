package com.evertix.tutofastapi.controller;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.controller.constants.ResponseConstants;
import com.evertix.tutofastapi.security.request.LoginRequest;
import com.evertix.tutofastapi.security.request.SignUpRequest;
import com.evertix.tutofastapi.security.response.JwtResponse;
import com.evertix.tutofastapi.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@CrossOrigin
@Tag(name = "Authentication", description = "API is Ready")
@RequestMapping("api/auth")
@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/signup")
    @Operation(summary = "User Registration", description = "Registration for both teacher and student user", tags = {"Authentication"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        return this.authenticationService.registerUser(signUpRequest);
    }

    @PostMapping("/signin")
    @Operation(summary = "User Log in", description = "Log in for teacher, student and admin user. Returns JWT and user info", tags = {"Authentication"})
    public ResponseEntity<MessageResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        MessageResponse response;
        try {
            JwtResponse jwtResponse = this.authenticationService.authenticateUser(loginRequest);
            response = MessageResponse.builder().code(ResponseConstants.SUCCESS_CODE).message("Se autenticó correctamente").data(jwtResponse).build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch(BadCredentialsException e){
            response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("Las credenciales no son correctas").build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e){
            response = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message(e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
}
