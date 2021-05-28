package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.Engine;
import com.cg.dto.Flight;
import com.cg.repository.EngineRepository;
import com.cg.repository.FlightRepository;

@RestController
@RequestMapping("/flights")
public class FlightController {
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private EngineRepository engineRepository;
	
	@PostMapping()
	public ResponseEntity<?> addFlight(@RequestBody Flight flight) {
		Engine engine = flight.getEngine();
		if(engine!=null)
			engineRepository.save(engine);  //Save child first, then parent
		flightRepository.save(flight);
		return new ResponseEntity<>("Inserted successfully",HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> test() {
		return new ResponseEntity<>(flightRepository.findAll(),HttpStatus.OK);
	}

}
