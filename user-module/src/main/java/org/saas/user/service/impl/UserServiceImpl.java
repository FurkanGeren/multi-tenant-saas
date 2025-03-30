package org.saas.user.service.impl;
import org.saas.core.domain.SubscriptionInfo;
import org.saas.core.dto.AuthUser;
import org.saas.core.dto.AuthUserRequest;
import org.saas.core.exception.BusinessException;
import org.saas.core.tenant.TenantContext;
import org.saas.core.tenant.TenantInfoProvider;
import org.saas.user.dto.CreateUserRequest;
import org.saas.user.dto.UserResponse;
import org.saas.core.domain.User;
import org.saas.user.repository.UserRepository;
import org.saas.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TenantInfoProvider tenantInfoProvider;

    public UserServiceImpl(UserRepository userRepository, TenantInfoProvider tenantInfoProvider) {
        this.userRepository = userRepository;
        this.tenantInfoProvider = tenantInfoProvider;
    }


    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        SubscriptionInfo tenant = tenantInfoProvider.getCurrentTenantInfo();

        TenantContext.setTenantSchema(tenant.schema());


        System.out.println("Tenant Name: " + tenant.tenantName());
        System.out.println("Schema: " + tenant.schema());
        System.out.println("Max Users: " + tenant.maxUsers());


        long currentUserCount = userRepository.count();
        if (currentUserCount >= tenant.maxUsers()) {
            throw new BusinessException("Kullanıcı limiti aşıldı. Maksimum izin verilen: " + tenant.maxUsers());
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Bu e-posta adresi zaten kayıtlı.");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPassword(request.password());

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getFullName());
    }

    @Override
    public AuthUser getByEmail(AuthUserRequest authUserRequest) {
       // SubscriptionInfo tenant = tenantInfoProvider.getCurrentTenantInfo();
        //TenantContext.setTenantSchema(authUserRequest.schema());

        User user = userRepository.findByEmail(authUserRequest.email())
                .orElseThrow(() -> new RuntimeException("/hataaa")); // TODO

        if (!user.getPassword().equals(authUserRequest.password())) {
            throw new RuntimeException("Hata"); // TODO
        }

        TenantContext.clear();
        return new AuthUser(user.getId(),
                user.getEmail(),
                user.getPassword());
    }

}