package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.users.UsersPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface UsersPasswordRepository extends JpaRepository<UsersPassword, String> {

    public Optional<UsersPassword> findByUsersEmailAndActive(String email, Boolean active);

    Optional<UsersPassword> findByUsersIdAndActive(String id, Boolean active);
}
