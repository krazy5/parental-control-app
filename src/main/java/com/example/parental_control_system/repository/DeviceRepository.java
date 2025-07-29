package com.example.parental_control_system.repository;

import com.example.parental_control_system.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    // Custom finder by deviceId
    Device findByDeviceId(String deviceId);
}
