package com.cg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.dto.Engine;

public interface EngineRepository extends MongoRepository<Engine, String> {

}
