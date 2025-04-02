package org.saas.user.service.impl;
import org.saas.core.context.ActorContext;
import org.saas.core.domain.Role;
import org.saas.core.domain.SubscriptionInfo;
import org.saas.core.dto.AuthUser;
import org.saas.core.dto.AuthUserRequest;
import org.saas.core.exception.BusinessException;
import org.saas.core.context.TenantContext;
import org.saas.core.tenant.TenantInfoProvider;
import org.saas.user.dto.CreateUserRequest;
import org.saas.user.dto.UserResponse;
import org.saas.core.domain.User;
import org.saas.user.repository.RoleRepository;
import org.saas.user.repository.UserRepository;
import org.saas.user.service.UserService;
import org.saas.core.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TenantInfoProvider tenantInfoProvider;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TenantInfoProvider tenantInfoProvider, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tenantInfoProvider = tenantInfoProvider;
        this.jwtUtil = jwtUtil;
    }


    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        SubscriptionInfo tenant = tenantInfoProvider.getCurrentTenantInfo();

        TenantContext.setTenantSchema(tenant.schema());

        long currentUserCount = userRepository.count();
        if (currentUserCount >= tenant.maxUsers()) {
            throw new BusinessException("Kullanıcı limiti aşıldı. Maksimum izin verilen: " + tenant.maxUsers());
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Bu e-posta adresi zaten kayıtlı.");
        }


        ActorContext.setActor(jwtUtil.extractUsername());

        Role role = roleRepository.findById(request.roleId())
                .orElseThrow();  // TODO

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPassword(request.password());
        user.setRole(role);

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getRole().getRoleName());
    }

    @Override
    public AuthUser getByEmail(AuthUserRequest authUserRequest) {
        User user = userRepository.findByEmail(authUserRequest.email())
                .orElseThrow(() -> new RuntimeException("/hataaa")); // TODO

        if (!user.getPassword().equals(authUserRequest.password())) {
            throw new RuntimeException("Hata"); // TODO
        }

        return new AuthUser(user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getRole().getRoleName());
    }

}