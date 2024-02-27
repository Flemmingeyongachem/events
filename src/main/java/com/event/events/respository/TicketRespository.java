package com.event.events.respository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import com.event.events.entity.Ticket;

public interface TicketRespository extends JpaRepository<Ticket, Long>{
    Set<Ticket> findByEventId(int eventId);

    boolean existsById(Long id);

    boolean existsByIdAndEventNotNull(Long id);
    
}
