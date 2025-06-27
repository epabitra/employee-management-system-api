
package com.nihar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity  // ✅ This enables @PreAuthorize, @PostAuthorize, etc.
public class MethodSecurityConfig {
    // No code needed inside; annotation is enough
}
