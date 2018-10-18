package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface UsersRepository extends JpaRepository<Users, String> {

    public Optional<Users> findByEmail(String email);
}
