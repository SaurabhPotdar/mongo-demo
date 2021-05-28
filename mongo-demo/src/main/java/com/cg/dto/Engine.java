package com.cg.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("engines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Engine {
	
	@Id
	private String id;
	private String name;

}
