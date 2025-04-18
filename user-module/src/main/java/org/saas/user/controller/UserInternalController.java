package org.saas.user.controller;


import org.saas.core.context.TenantContext;
import org.saas.core.dto.AuthUser;
import org.saas.core.dto.AuthUserRequest;
import org.saas.core.dto.UserResponse;
import org.saas.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internal/users")
public class UserInternalController {


    private final UserService userService;

    public UserInternalController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/email")
    public ResponseEntity<AuthUser> getByEmail(@RequestBody AuthUserRequest authUserRequest) {
        return ResponseEntity.ok(userService.getByEmail(authUserRequest));
    }

    @GetMapping("/moderators")
    public ResponseEntity<List<UserResponse>> getModerators(@RequestParam("schema") String schema) {
        return ResponseEntity.ok(userService.getModerators(schema));
    }
}
