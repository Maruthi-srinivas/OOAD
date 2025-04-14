package com.example.Telecom_buliding_system.repository;

import com.example.Telecom_buliding_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}