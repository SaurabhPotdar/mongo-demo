package com.cg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.dto.Flight;

public interface FlightRepository extends MongoRepository<Flight, String> {

}
