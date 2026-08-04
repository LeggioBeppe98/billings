package com.bldev.billings.repository;

import com.bldev.billings.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<User> findByAttivo(boolean attivo, Pageable pageable);
    Page<User> findByNomeContainingIgnoreCase(String name, Pageable pageable);
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    User findUserById(Long id);
}
