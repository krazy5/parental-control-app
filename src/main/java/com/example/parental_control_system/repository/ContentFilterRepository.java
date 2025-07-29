// 10. ContentFilterRepository.java
package com.example.parental_control_system.repository;

import com.example.parental_control_system.entity.ContentFilter;
import com.example.parental_control_system.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentFilterRepository extends JpaRepository<ContentFilter, Long> {
    List<ContentFilter> findByChildAndIsActiveTrue(Child child);
}
