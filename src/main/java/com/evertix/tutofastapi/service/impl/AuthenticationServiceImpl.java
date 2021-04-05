package com.evertix.tutofastapi.service.impl;

import com.evertix.tutofastapi.model.Role;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.enums.ERole;
import com.evertix.tutofastapi.repository.RoleRepository;
import com.evertix.tutofastapi.repository.UserRepository;
import com.evertix.tutofastapi.security.jwt.JwtUtils;
import com.evertix.tutofastapi.security.request.LoginRequest;
import com.evertix.tutofastapi.security.request.SignUpRequest;
import com.evertix.tutofastapi.security.response.JwtResponse;
import com.evertix.tutofastapi.security.response.Message;
import com.evertix.tutofastapi.security.service.UserDetailsImpl;
import com.evertix.tutofastapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Message("Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Message("Email is already taken"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getName(),
                signUpRequest.getLastName(), signUpRequest.getDni(), signUpRequest.getPhone());

        String strRole = signUpRequest.getRole();
        Role userRole;

        if (strRole == null) {
            return ResponseEntity.badRequest().body(new Message("Role must not be null ):"));
        } else {
            switch (strRole) {
                case "ROLE_ADMIN":
                    userRole  = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    break;
                case "ROLE_STUDENT":
                    userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                            .orElseThrow(() -> new RuntimeException("Error: Role student is not found. :("));


                    break;
                case "ROLE_TEACHER":
                    userRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                            .orElseThrow(() -> new RuntimeException("Error: Role teacher is not found. :("));

                    break;
                default:
                    throw new RuntimeException("Error: Role is wrong.");

            }


        }

        user.setRole(userRole);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        String roleOne="";
        for(String r: roles){
            roleOne=roleOne.concat(r);
        }

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roleOne, userDetails.getName(),
                userDetails.getLastName(), userDetails.getDni(), userDetails.getPhone());
    }
}
