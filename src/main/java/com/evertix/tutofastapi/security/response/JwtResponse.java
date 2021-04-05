package com.evertix.tutofastapi.security.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private Long id;
    private String username;
    private String email;
    ///private List<String> roles;
    private String role;
    private String name;
    private String lastName;
    private String dni;
    private String phone;


    //List<String>
    public JwtResponse(String accessToken, Long id, String username, String email, String roles, String name,
                       String lastName, String dni, String phone){
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = roles;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;

    }
}
