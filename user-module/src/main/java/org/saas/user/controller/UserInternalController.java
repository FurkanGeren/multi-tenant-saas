package org.saas.user.controller;


import org.saas.core.dto.AuthUser;
import org.saas.core.dto.AuthUserRequest;
import org.saas.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
