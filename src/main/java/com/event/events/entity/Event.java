package com.event.events.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
// import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import com.event.events.entity.Ticket;
// import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
@Transactional
@Entity
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "description")
    private String description;


    @Column(name = "expired")
    private boolean expired;

    @Column(name="eventDate")
    private LocalDate eventDate;

    @Column(name="imageUrl", length=500)
    private String imageUrl;

    @Column(name = "startTime")
    private LocalTime startTime;
    
    @Column(name = "endTime")
    private LocalTime endTime;

    @Column(name = "created_on")
    @CreationTimestamp // this adds the default timestamp on save
    private Timestamp created_on;


    @UpdateTimestamp
    @Column(name="updated_on")
    private Timestamp updated_on;

    // @JsonIgnore
    @OneToOne(cascade=CascadeType.ALL)
    private Location location;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="artist_id")
    private User artist;

    // @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Ticket> tickets;
    
}
