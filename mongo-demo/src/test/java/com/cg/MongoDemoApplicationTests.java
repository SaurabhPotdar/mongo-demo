package com.cg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.dto.Engine;
import com.cg.dto.Flight;
import com.cg.repository.FlightRepository;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class MongoDemoApplicationTests {

	@Autowired
	private FlightRepository flightRepository;

	@BeforeEach
	public void beforeEach() {
		
		Flight flight1 = new Flight("1d1aab22-670b-48cb-a027-721e2055731f", "Pune", 2, null);

		Flight flight2 = new Flight("d04a8c26-7527-407c-81ef-680e5de34cea", "Banglore", 4, null);

		Flight flight3 = new Flight("7ed990d2-6471-485d-931e-c77729dc0f25", "Hydrebad", 5,
				new Engine("b8b50563-ca90-4afc-9d43-b674892a525c", "engine1"));
		
		List<Flight> flights = Arrays.asList(flight1,flight2,flight3);
		flightRepository.saveAll(flights);
	}
	
	@AfterEach
    public void afterEach() {
        flightRepository.deleteAll();
    }
	
	@Test
	public void testInsert() {
		assertEquals(3, flightRepository.count());
	}
	
	@Test
	@DisplayName("Test query:findNbSeats")
	public void testQueryFindNbSeats() {
		int minNbSeats = 0;
		assertEquals(2, flightRepository.findByMinNbSeats(minNbSeats));
	}

}
