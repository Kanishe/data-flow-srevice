package com.example.dataflowsrevice.repository;

import com.example.dataflowsrevice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
