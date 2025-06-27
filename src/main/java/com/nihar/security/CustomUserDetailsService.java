package com.nihar.security;

import com.nihar.entity.User;
import com.nihar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user from database using email (as username)
     * and builds Spring Security's UserDetails with role authorities.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Set<SimpleGrantedAuthority> authorities = user.getUserRoleDepartment().stream()
            .filter(urd -> urd.getRole() != null && urd.getRole().getName() != null)
            .map(urd -> new SimpleGrantedAuthority("ROLE_" + urd.getRole().getName().toUpperCase()))
            .collect(Collectors.toSet());
        
        System.out.println("User: " + user.getEmail());
        System.out.println("Roles: " + user.getUserRoleDepartment().stream()
                .map(urd -> urd.getRole().getName())
                .collect(Collectors.toList()));
        System.out.println("Authorities: " + authorities);


        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPasswordHash(),
            authorities
        );
    }
}
