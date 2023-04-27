package com.bci.entity;

// User.java

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)

@Data
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(nullable = true)
    private String name;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneEntity> phones = new ArrayList<>();
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
}
