package com.event.events.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "number_bought")
    private int number_bought;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp created_at;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_tickets")
    private Event event;

    // @JsonManagedReference
    // @JsonBackReference
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_tickets")
    private User user;

    // @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_cat_id")
    private Category category;

}