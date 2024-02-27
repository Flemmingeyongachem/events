package com.event.events.respository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.events.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Page<Event> findByExpired(boolean expired, Pageable pageable);

    Page<Event> findByNameContaining(String name, Pageable pageable);
     
}
