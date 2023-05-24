package com.example.dataflowsrevice.repository;

import com.example.dataflowsrevice.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event>findByType(String string, Pageable pageable);
}
