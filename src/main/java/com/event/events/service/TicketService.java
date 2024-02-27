package com.event.events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.event.events.respository.EventRepository;
import com.event.events.respository.TicketRespository;
import java.util.List;
import java.util.Set;

import com.event.events.entity.Ticket;
import com.event.events.exceptions.EventNotFound;
import com.event.events.entity.Event;

@Service
public class TicketService {
    @Autowired
    TicketRespository respository;

    @Autowired
    EventRepository eventrespository;

    public List<Ticket> get_tickets() {
        return respository.findAll();
    }


    public void create_ticket(Ticket ticket) {
            respository.save(ticket);
    }

    public Ticket getTicketById(Long id){
        return respository.findById(id).orElse(null);
    }
    public Set<Ticket> getTicketsByEventId(int eventId) {
            return respository.findByEventId(eventId);
        }

    public Ticket addTicketToEvent(int eventId, Ticket ticket) {
        Ticket tk = respository.findById(ticket.getId()).orElseThrow(()-> new IllegalArgumentException("Ticket with that info doesnot exist!!."));
        // Check if the ticket is already associated with an event
        if (ticket.getEvent() != null) {
            throw new IllegalStateException("Ticket is already associated with an event.");
        }    

        // Check if the ticket is used in another event
        if (respository.existsByIdAndEventNotNull(ticket.getId())) {
            throw new IllegalArgumentException("Ticket is already used in another event.");
        }
        
        Event event = eventrespository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + eventId));

            ticket.setEvent(event);
            return respository.save(ticket);
        }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}

