package com.event.events.entity;


import java.security.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import com.event.events.entity.Ticket;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Transactional
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;
    
    @Column(name="password", length = 100)
    private String password;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name="artist")
    private boolean artist;

    @Column(name = "joined_on")
    @CreationTimestamp
    private Timestamp joined_on;

    @Column(name="last_active")
    @UpdateTimestamp
    private Timestamp last_active;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    
}
