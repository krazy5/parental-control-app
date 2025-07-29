package com.example.parental_control_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "children")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate dateOfBirth;
    private String profilePicture;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private List<Device> devices = new ArrayList<>();

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

    public void setParent(User parent) {
    }

    public Arrays getActivities() {
        return null;
    }
}
