package com.cg.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cg.dto.Flight;

public interface FlightRepository extends MongoRepository<Flight, String> {
	
	@Query("{'flight.noOfSeats' : {$gte: ?0}}")
    List<Flight> findByMinNbSeats(int minNbSeats);

}
