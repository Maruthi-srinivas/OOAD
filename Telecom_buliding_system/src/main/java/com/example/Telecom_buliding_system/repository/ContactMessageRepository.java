
package com.example.Telecom_buliding_system.repository;

import com.example.Telecom_buliding_system.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}