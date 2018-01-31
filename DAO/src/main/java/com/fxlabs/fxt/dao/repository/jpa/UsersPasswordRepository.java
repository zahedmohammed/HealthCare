package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.UsersPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersPasswordRepository extends JpaRepository<UsersPassword, String> {

    public Optional<UsersPassword> findByUsersEmailAndActive(String email, Boolean active);
}
