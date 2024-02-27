package com.event.events.controller;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.event.events.entity.Event;
import com.event.events.entity.Ticket;
import com.event.events.respository.EventRepository;
import com.event.events.service.TicketService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
public class EventController {
    @Autowired
    EventRepository repository;

    @Autowired
    TicketService ticketService;

    @GetMapping("/events/")
    public ResponseEntity<Map<String, Object>> getAllEvents(
        @RequestParam(required = false) String name,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "8") int size
      ) {

    try {
      List<Event> events = new ArrayList<Event>();
      Pageable paging = PageRequest.of(page, size);
      
      Page<Event> pageEvents;
      if (name == null)
        pageEvents = repository.findAll(paging);
      else
        pageEvents = repository.findByNameContaining(name, paging);

      events = pageEvents.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("events", events);
      response.put("currentPage", pageEvents.getNumber());
      response.put("totalItems", pageEvents.getTotalElements());
      response.put("totalPages", pageEvents.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
        System.out.println(e.toString());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/events/passed/")
  public ResponseEntity<Map<String, Object>> findPassedEvents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "8") int size
      ) {
    try {      
      List<Event> events = new ArrayList<Event>();
      Pageable paging = PageRequest.of(page, size);
      
      Page<Event> pageEvents = repository.findByExpired(true, paging);
      events = pageEvents.getContent();
            
      Map<String, Object> response = new HashMap<>();
      response.put("events", events);
      response.put("currentPage", pageEvents.getNumber());
      response.put("totalItems", pageEvents.getTotalElements());
      response.put("totalPages", pageEvents.getTotalPages());
      
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

    // @GetMapping("/events/")
    // public List<Event> get_events(){
    //     return repository.findAll();
    // }

    @GetMapping("/event/{id}/")
    public Event get_eventbyId(@PathVariable int id){
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/event/add/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add_event(@RequestBody Event event){
        repository.save(event);
    }

    @PutMapping("event/update/{id}/")
    public Event update_event(@PathVariable int id , @RequestBody Event event){
        Event updatedevent = repository.findById(id).get();
        updatedevent.setDescription(event.getDescription());
        updatedevent.setName(event.getName());
        updatedevent.setExpired(event.isExpired());
        return repository.save(updatedevent);
    }

    @PutMapping("event/{event_id}/add-ticket/")
    @ResponseStatus(code=HttpStatus.OK)
    public ResponseEntity<Ticket> addTicketToEvent(
            @PathVariable("event_id") int event_id,
            @RequestBody Ticket ticket) {
        Ticket addedTicket = ticketService.addTicketToEvent(event_id, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTicket);
    }

    @GetMapping("event/{event_id}/tickets/")
    public Set<Ticket> getTicketsForEvent(@PathVariable("event_id") int event_id) {
        return ticketService.getTicketsByEventId(event_id);
    }

    
}
