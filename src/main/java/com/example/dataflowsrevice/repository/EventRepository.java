package com.example.dataflowsrevice.repository;

import com.example.dataflowsrevice.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Slice<Event> findByType(String string, Pageable pageable);
}
