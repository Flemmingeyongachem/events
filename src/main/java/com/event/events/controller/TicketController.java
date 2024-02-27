package com.event.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.event.events.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.event.events.entity.Ticket;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class TicketController {
    @Autowired
    TicketService service;

    @GetMapping("/tickets/")
    public List<Ticket> get_tickets_list() {
        return service.get_tickets();
    }

    @GetMapping("/ticket/{id}/")
    public Ticket get_ticketById(@PathVariable Long id) {
        return service.getTicketById(id);
    }

    @PostMapping("/new-ticket/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void postMethodName(@RequestBody Ticket ticket) {
        service.create_ticket(ticket);
    }


    
    
}
