package com.example.parental_control_system.repository;

import com.example.parental_control_system.entity.DeviceCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceCommandRepository extends JpaRepository<DeviceCommand, Long> {
    // No additional methods needed for basic CRUD
}
