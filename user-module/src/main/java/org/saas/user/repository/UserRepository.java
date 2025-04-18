package org.saas.user.repository;

import org.saas.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    long count();

    Optional<User> findByEmail(String email);

    List<User> findByRole_RoleNameIgnoreCase(String moderator);
}
