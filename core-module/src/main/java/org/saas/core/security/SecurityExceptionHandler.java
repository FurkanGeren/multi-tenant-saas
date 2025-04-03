package org.saas.core.security;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class SecurityExceptionHandler {


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(
                Map.of(
                        "status", 403,
                        "message", "Bu işlemi yapmak için yetkiniz yok."
                )
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(
                Map.of(
                        "status", 401,
                        "message", "Kimlik doğrulama başarısız. Lütfen geçerli bir token sağlayın."
                )
        );
    }
}
