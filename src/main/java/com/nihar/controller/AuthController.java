package com.nihar.controller;

import com.nihar.util.JwtUtil;

import com.nihar.repository.UserRepository;
import com.nihar.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private final CustomUserDetailsService userDetailsService;
	
	@Autowired
    private final JwtUtil jwtUtil;
	
	@Autowired
    private final UserRepository userRepository; 
	

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) {
    	System.err.println("AuthController.login()");
        String email = loginData.get("email");
        String password = loginData.get("password");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtUtil.generateToken(userDetails); 

        
        System.out.println(userDetails.getUsername());
        System.out.println(token);
        

        return Map.of("token", token);
    }
}
