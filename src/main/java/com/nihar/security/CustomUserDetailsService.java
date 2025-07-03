package com.nihar.security;

import com.nihar.entity.User;
import com.nihar.entity.UserRoleDepartment;
import com.nihar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            .orElseThrow(() -> new UsernameNotFoundException("❌ User not found with email: " + email));

        // Handle case where roles might be null or empty
        Set<SimpleGrantedAuthority> authorities = user.getUserRoleDepartment() != null
            ? user.getUserRoleDepartment().stream()
                .map(UserRoleDepartment::getRole)
                .filter(role -> role != null && role.getName() != null)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                .collect(Collectors.toSet())
            : Collections.emptySet();

        // ✅ Optional logging for debugging
        System.out.println("🔐 Auth User Email: " + user.getEmail());
        System.out.println("✅ Roles: " + authorities);

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPasswordHash(),
            authorities
        );
    }
}
