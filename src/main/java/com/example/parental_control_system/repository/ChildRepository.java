package com.example.parental_control_system.repository;

import com.example.parental_control_system.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findByParentId(Long parentId);

    int         countByParentId(Long parentId);

    Optional<Child> findByIdAndParentId(Long id, Long parentId);
}


