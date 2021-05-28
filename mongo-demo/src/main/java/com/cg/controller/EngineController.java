package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cg.dto.Engine;
import com.cg.repository.EngineRepository;

public class EngineController {
	
	@Autowired
	private EngineRepository engineRepository;
	
	@PostMapping()
	public ResponseEntity<?> addEngine(@RequestBody Engine engine) {
		System.out.println(engine);
		engineRepository.save(engine);
		return new ResponseEntity<>("Inserted successfully",HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> test() {
		return new ResponseEntity<>(engineRepository.findAll(),HttpStatus.OK);
	}

}
