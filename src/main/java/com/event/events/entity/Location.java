package com.event.events.entity;

import jakarta.transaction.Transactional;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Data
@Setter
@Getter
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="location")
public class Location {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long loc_id;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name="", referencedColumnName="id")
    // private List<Event> event;

    @ManyToMany
    @JoinTable(name="location_events",
    joinColumns = @JoinColumn(name="loc_id"),
    inverseJoinColumns = @JoinColumn(name="id")
    )
    private Set<Event> eventset = new HashSet<>();


    
    
}
