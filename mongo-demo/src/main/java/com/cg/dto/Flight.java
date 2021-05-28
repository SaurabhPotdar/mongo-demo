package com.cg.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Flight {
	
	@Id
	private String id;  //Mongo uses object id, hence type is String
	
	@Field("departure")
	@TextIndexed
	private String departureCity;
	
	@Indexed(direction = IndexDirection.ASCENDING)
	private int noOfSeats;
	
	@DBRef
	private Engine engine;
	
}
