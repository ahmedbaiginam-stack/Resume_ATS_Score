package com.Resume.ATS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Resume.ATS.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);
}