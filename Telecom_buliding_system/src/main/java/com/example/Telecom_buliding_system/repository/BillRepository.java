package com.example.Telecom_buliding_system.repository;

import com.example.Telecom_buliding_system.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUserId(Long userId); // Fetch bills by user ID
    List<Bill> findByConflictRaised(boolean conflictRaised);
}
