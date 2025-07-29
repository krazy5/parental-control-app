// 11. NotificationRepository.java
package com.example.parental_control_system.repository;

import com.example.parental_control_system.entity.ParentNotification;
import com.example.parental_control_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<ParentNotification, Long> {
    List<ParentNotification> findByParent(User parent);
}
