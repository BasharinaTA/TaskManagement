package com.basharina.taskmanagementsystem.model.entity;


import com.basharina.taskmanagementsystem.model.Role;
import com.basharina.taskmanagementsystem.model.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="email")
    private String email;

    @Column(name="hash_password")
    private String hashPassword;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @CreationTimestamp
    @Column(name="created")
    private LocalDateTime created;
}
