package com.event.events.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event.events.entity.Location;

public interface LocationRespository  extends JpaRepository<Location, Long>{
    
}
