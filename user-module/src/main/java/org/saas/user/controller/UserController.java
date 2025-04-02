package org.saas.user.controller;


import jakarta.validation.Valid;
import org.saas.core.annotation.Auditable;
import org.saas.core.annotation.ModuleAccess;
import org.saas.core.domain.enums.ModuleType;
import org.saas.user.dto.CreateUserRequest;
import org.saas.user.dto.UserResponse;
import org.saas.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @Auditable(action = "CREATE", resource = "User")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/test")
    @ModuleAccess(ModuleType.USER)
    public String test() {
        return "Accessed!";
    }
}
