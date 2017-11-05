package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, String> {
}
